package run.freshr.domain.common.service;

import run.freshr.domain.common.dto.send.EmailSend;

public interface MailUnit {

  void send(String subject, String templatePath, EmailSend dto);

}
