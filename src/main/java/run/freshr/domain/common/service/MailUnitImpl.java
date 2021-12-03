package run.freshr.domain.common.service;

import static com.fasterxml.jackson.core.JsonEncoding.UTF8;
import static java.util.Objects.isNull;
import static run.freshr.common.util.RestUtil.getConfig;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.spring5.SpringTemplateEngine;
import run.freshr.annotation.Unit;
import run.freshr.domain.common.dto.send.EmailSend;

@Slf4j
@Unit
@RequiredArgsConstructor
public class MailUnitImpl implements MailUnit {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;

  @Override
  public void send(String subject, String templatePath, EmailSend dto) {
    log.info("MailUnit.send");

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
