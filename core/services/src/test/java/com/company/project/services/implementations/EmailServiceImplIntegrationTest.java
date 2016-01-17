package com.company.project.services.implementations;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    private static final String SPANISH = "es";
    private static final String PORTUGUES = "pt";

    private @Autowired EmailService emailService;

    @Before
    public void setUp() {
    }

    @Ignore
    @Test
    public void testSendSimpleEmail() throws InterruptedException, ExecutionException {
        String to = "alan.albertengo@gmail.com";
        String subject = "subject";
        String body = "body";
        Future<Boolean> future = emailService.sendAsyncMail(to, subject, body);

        while (true) {
            if (future.isDone()) {
                System.out.println("Simple email sent = " + future.get());
                break;
            }
            System.out.println("Waiting to send Simple email. ");
            Thread.sleep(1000);
        }
    }

    @Ignore
    @Test
    public void testSendConfirmationEmail() throws InterruptedException, ExecutionException {
        String to = "alan.albertengo@gmail.com";
        String name = "Alan";

        Future<Boolean> future = emailService.sendConfirmationMessage(to, SPANISH, name, "linkValue");

        while (true) {
            if (future.isDone()) {
                System.out.println("Confirmation email sent = " + future.get());
                break;
            }
            System.out.println("Waiting to send Confirmation email.");
            Thread.sleep(1000);
        }
    }

    @Ignore
    @Test
    public void testSendForgetPasswordEmail() throws InterruptedException, ExecutionException {
        String name = "Alan";
        String to = "alan.albertengo@gmail.com";
        Future<Boolean> future = emailService.sendForgotPasswordMessage(to, PORTUGUES, name, "linkValue");

        while (true) {
            if (future.isDone()) {
                System.out.println("Forgot Password email sent = " + future.get());
                break;
            }
            System.out.println("Waiting to send Forgot Password email.");
            Thread.sleep(1000);
        }
    }

}
