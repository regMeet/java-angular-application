package com.company.project.services.interfaces;

import java.util.Locale;
import java.util.Map;

public interface EmailService {

    void sendMail(String to, String subject, String body);

	void sendHTMLMessage(String to, String subject, Locale locale, String templateName, Map<String, String> templateVars);

	void sendConfirmationMessage(String to, String subject, Locale locale, String name, String link);

}