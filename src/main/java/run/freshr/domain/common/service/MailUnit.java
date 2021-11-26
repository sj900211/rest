package run.freshr.domain.common.service;

import static com.fasterxml.jackson.core.JsonEncoding.UTF8;
import static java.util.Objects.isNull;
import static run.freshr.common.util.RestUtil.getConfig;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import run.freshr.annotation.Unit;
import run.freshr.domain.common.dto.send.EmailSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * The Class Mail unit.
 *
 * @author [류성재]
 * @implNote 공통 관리 > 이메일 발송 Service
 * @since 2021. 3. 16. 오후 2:42:57
 */
@Unit
@Slf4j
@RequiredArgsConstructor
public class MailUnit {

  /**
   * The Mail sender
   */
  private final JavaMailSender mailSender;
  /**
   * The Template engine
   */
  private final SpringTemplateEngine templateEngine;

  /**
   * Send.
   *
   * @param subject      the subject
   * @param templatePath the template path
   * @param dto          the dto
   * @author [류성재]
   * @implNote 발송
   * @since 2021. 3. 16. 오후 2:42:57
   */
  public void send(String subject, String templatePath, EmailSend dto) {
    try {
      String html = templateEngine.process(templatePath, dto.getContext());

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, UTF8.getJavaName());

      messageHelper.setTo(dto.getAddress());
      messageHelper.setFrom(getConfig().getEmailFrom());
      messageHelper.setSubject(subject);
      messageHelper.setText(html, true);

      if (!isNull(dto.getAttachList())) {
        dto.getAttachList().forEach(attach -> {
          try {
            FileDataSource fileDataSource = new FileDataSource(attach.getPhysical());

            messageHelper.addAttachment(attach.getDisplay(), fileDataSource);
          } catch (MessagingException e) {
            e.printStackTrace();
          }
        });
      }

      mailSender.send(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
