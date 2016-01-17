package com.company.project.services.implementations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.company.project.services.interfaces.EmailService;
import com.company.project.services.utils.InternationalizationService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Transactional
@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger log = Logger.getLogger(EmailServiceImpl.class);

    private static final String CONFIRMATION_TEMPLATE = "confirmation.ftl";
    private static final String FORGOT_PASSWORD_TEMPLATE = "forgotPassword.ftl";

    private static final String CONFIRMATION_SUBJECT = "email.confirmation.subject";
    private static final String NEW_PASSWORD_SUBJECT = "email.forgot.password.subject";

    private final InternationalizationService i18nService;
    private JavaMailSender mailSender;
    private Configuration config;

    @Value("${smtp.from}")
    private String name;

    @Value("${smtp.username}")
    private String email;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, Configuration config, InternationalizationService i18nService) {
        this.mailSender = mailSender;
        this.config = config;
        this.i18nService = i18nService;
    }

    private Template getTemplate(String templateName, Locale locale) throws IOException, TemplateException {
        return config.getTemplate(templateName, locale);
    }

    @Async
    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendHTMLMessage(String to, String subject, Locale locale, String templateName, Map<String, String> templateVars) throws Exception {
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
    }

    @Async
    @Override
    public Future<Boolean> sendAsyncMail(String to, String subject, String body) {
        try {
            sendMail(to, subject, body);

            return new AsyncResult<Boolean>(true);
        } catch (Exception e) {
            log.error("The async email has not been send to: " + to, e);
            return new AsyncResult<Boolean>(false);
        }
    }

    @Async
    @Override
    public Future<Boolean> sendConfirmationMessage(String to, String language, String name, String link) {
        try {
        Map<String, String> templateVars = new HashMap<String, String>();
        templateVars.put("name", name);
        templateVars.put("confirmationLink", link);

            String subject = i18nService.getMessage(CONFIRMATION_SUBJECT, language);
            sendHTMLMessage(to, subject, new Locale(language), CONFIRMATION_TEMPLATE, templateVars);

            return new AsyncResult<Boolean>(true);
        } catch (Exception e) {
            log.error("The confirmation email has not been send to: " + to, e);
            return new AsyncResult<Boolean>(false);
        }
    }

    @Async
    @Override
    public Future<Boolean> sendForgotPasswordMessage(String to, String language, String name, String link) {
        try {
        Map<String, String> templateVars = new HashMap<String, String>();
        templateVars.put("name", name);
        templateVars.put("forgotPasswordLink", link);
        // TODO: create new link
        templateVars.put("forgotPasswordNotRequestedLink", "create new link");

            String subject = i18nService.getMessage(NEW_PASSWORD_SUBJECT, language);
            sendHTMLMessage(to, subject, new Locale(language), FORGOT_PASSWORD_TEMPLATE, templateVars);

            return new AsyncResult<Boolean>(true);
        } catch (Exception e) {
            log.error("The confirmation email has not been send to: " + to, e);
            return new AsyncResult<Boolean>(false);
        }
    }

}