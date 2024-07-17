package ins.ashok.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailGenerator {
     @Autowired
	private JavaMailSender mailsender;

	public boolean sendemail(String subject, String body, String to,File f) {
		try {
		MimeMessage mimssg = mailsender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimssg,true);
	
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setTo("anilbaral061@gmail.com");
			helper.addAttachment("file-info",f);
			mailsender.send(mimssg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}

}
