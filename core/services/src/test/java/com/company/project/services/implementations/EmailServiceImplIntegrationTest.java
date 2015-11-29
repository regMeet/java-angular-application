package com.company.project.services.implementations;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.project.services.interfaces.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class EmailServiceImplIntegrationTest {

	@Autowired
	private EmailService emailService;

	@Before
	public void setUp() {
	}

//	@Ignore
	@Test
	public void testSendSimpleEmail() {
		String to = "alan.albertengo@gmail.com";
		String subject = "subject";
		String body = "body";
		emailService.sendMail(to, subject, body);
	}

	@Ignore
	@Test
	public void testSendHTMLEmail() {
		String to = "alan.albertengo@gmail.com";
		String subject = "subject";
		emailService.sendConfirmationMessage(to, subject, EmailServiceImpl.SPANISH, "Alan", "linkValue");
	}

}
