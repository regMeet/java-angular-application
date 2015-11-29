package com.company.project.services.implementations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.company.project.services.interfaces.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Transactional
@Service
public class EmailServiceImpl implements EmailService {
	private static final String CONFIRMATION_TEMPLATE = "confirmation.ftl";
	public static final Locale SPANISH = new Locale("es");
	public static final Locale ENGLISH = Locale.ENGLISH;

	private JavaMailSender mailSender;
	private Configuration config;

	@Value("${smtp.from}")
	private String name;

	@Value("${smtp.username}")
	private String email;

	@Autowired
	public EmailServiceImpl(JavaMailSender mailSender, Configuration config) {
		this.mailSender = mailSender;
		this.config = config;
	}

	private Template getTemplate(String templateName, Locale locale) throws IOException, TemplateException {
		return config.getTemplate(templateName, locale);
	}

	@Async(value="executorService")
	@Override
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		// message.setFrom("Simple Application <clasificados2d@gmail.com>");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		System.out.println("aaa");
	}

	@Override
	public void sendHTMLMessage(String to, String subject, Locale locale, String templateName, Map<String, String> templateVars) {
		try {
			Template template = getTemplate(templateName, locale);
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateVars);

			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String from = String.format("%s <%s>", name, email);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(body, true);
			mailSender.send(message);

		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Async
	@Override
	public void sendConfirmationMessage(String to, String subject, Locale locale, String name, String link) {
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("name", name);
		templateVars.put("confirmationLink", link);

		sendHTMLMessage(to, subject, locale, CONFIRMATION_TEMPLATE, templateVars);
	}

}