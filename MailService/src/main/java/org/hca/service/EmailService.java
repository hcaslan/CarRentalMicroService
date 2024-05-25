package org.hca.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.hca.exception.ErrorType;
import org.hca.exception.MailServiceException;
import org.hca.model.MailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Async
    @RabbitListener(queues = "q.mail")
    public void send(MailModel mailModel) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(mailModel.getMessage(), true);
            helper.setTo(mailModel.getTo());
            helper.setSubject(mailModel.getSubject());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new MailServiceException(ErrorType.MAIL_SEND_FAIL);
        }
    }
}
