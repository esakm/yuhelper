package com.yuhelper.core.service;

import com.yuhelper.core.model.User;
import com.yuhelper.core.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    @Resource(name = "MailSenderBean")
    public JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    private static final String EMAIL_SIGN_UP_MESSAGE = "Your YUHelper verification link is https://yuhelper.ca/users/verify?token=%s";

    public void sendVerificationLink(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("YUHelper Verification Link");
        message.setText(String.format(EMAIL_SIGN_UP_MESSAGE, token));
        emailSender.send(message);
    }

    public void resendVerificationLink(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        if (user.isPresent() && user.get().getSignUpToken() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.get().getEmail());
            message.setSubject("YUHelper Verification Link");
            message.setText(String.format(EMAIL_SIGN_UP_MESSAGE, user.get().getSignUpToken().getToken()));
            emailSender.send(message);
        }

    }
}
