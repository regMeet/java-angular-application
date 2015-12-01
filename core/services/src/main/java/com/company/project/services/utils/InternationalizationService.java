package com.company.project.services.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class InternationalizationService {
	@Autowired
	private MessageSource messageSource;

	public String getMessage(String key, Locale locale, Object... args) {
		return messageSource.getMessage(key, args, locale);
	}

	public String getMessage(String key, String language, Object... args) {
		return messageSource.getMessage(key, args, new Locale(language));
	}

}
