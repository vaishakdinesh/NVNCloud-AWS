package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp {

    @Autowired
    private JavaMailSender eMailSender;

    private final String CONFIRMATION_URL = "/registration-confirmation?token=";
    private final String VERIFICATION_MESSAGE = "Click this link to activate your account with nvn-cloud.";

    public void sendVerificationMail(User user, String path){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("nvn-cloud Registration Confirmation");
        email.setText(VERIFICATION_MESSAGE + " rn" + path + CONFIRMATION_URL + user.getToken());
        eMailSender.send(email);
    }
}
