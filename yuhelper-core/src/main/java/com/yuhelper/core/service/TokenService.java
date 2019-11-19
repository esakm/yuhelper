package com.yuhelper.core.service;

import com.yuhelper.core.domain.security.model.SignUpToken;
import com.yuhelper.core.domain.security.repo.SignUpTokenRepository;
import com.yuhelper.core.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    SignUpTokenRepository signUpTokenRepository;

    @Autowired
    UserRepository userRepository;

    public Optional<SignUpToken> getSignUpToken(String token) {
        return signUpTokenRepository.findByToken(token);
    }

    @Transactional
    public void enableUser(SignUpToken token) {
        token.getUser().setEnabled(true);
        userRepository.merge(token.getUser());
        signUpTokenRepository.delete(token);
        signUpTokenRepository.flush();
    }
}
