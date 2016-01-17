package com.company.project.services.interfaces;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

public interface EmailService {

    void sendMail(String to, String subject, String body);

    void sendHTMLMessage(String to, String subject, Locale locale, String templateName, Map<String, String> templateVars) throws Exception;

    Future<Boolean> sendAsyncMail(String to, String subject, String body);

    Future<Boolean> sendConfirmationMessage(String to, String language, String name, String link);

    Future<Boolean> sendForgotPasswordMessage(String to, String language, String name, String link);

}