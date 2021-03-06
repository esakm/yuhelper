package com.yuhelper.core.service;

import com.yuhelper.core.domain.security.custom.CustomPasswordEncoder;
import com.yuhelper.core.domain.security.model.SignUpToken;
import com.yuhelper.core.domain.security.model.UserRole;
import com.yuhelper.core.domain.security.repo.RoleRepository;
import com.yuhelper.core.domain.security.repo.SignUpTokenRepository;
import com.yuhelper.core.domain.security.repo.UserRoleRepository;
import com.yuhelper.core.domain.security.service.UserSecurityService;
import com.yuhelper.core.model.User;
import com.yuhelper.core.model.UserInfo;
import com.yuhelper.core.repo.UserInfoRepository;
import com.yuhelper.core.repo.UserRepository;
import com.yuhelper.core.utils.DateFactory;
import com.yuhelper.core.utils.StringConverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    SignUpTokenRepository signUpTokenRepo;

    @Resource(name = "UserBean")
    User user;

    public boolean checkNewUsernameAndEmail(String username, String email) {
        Optional<User> usernameCheck = userRepository.getUserByUsername(username);
        Optional<User> emailCheck = userRepository.getUserByEmail(email);
        return !emailCheck.isPresent() && !usernameCheck.isPresent();
    }

    // CHECK IF 2 PASSWORDS ARE EQUAL
    public boolean checkPassword(String p1, String p2) {
        return p1.equals(p2);
    }

    public User createUser(String username, String email, String password) {
        CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
        String newSalt = passwordEncoder.getSalt();

        User user = new User(username, email, passwordEncoder.encode(password), newSalt);
        user = userRepository.saveAndFlush(user);

        UserRole userRole = new UserRole(user, roleRepository.findByName("ROLE_user"));
        userRoleRepository.saveAndFlush(userRole);

        SecureRandom random = new SecureRandom();
        byte[] newToken = new byte[32];
        random.nextBytes(newToken);
        SignUpToken signUpToken = new SignUpToken();
        signUpToken.setUser(user);
        signUpToken.setToken(StringConverter.bytesToHex(newToken));
        signUpToken.setExpiryTime(DateFactory.getShortTokenExpiryDate());
        signUpTokenRepo.saveAndFlush(signUpToken);

        user.setSignUpToken(signUpToken);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfoRepository.saveAndFlush(userInfo);

        user.setUserInfo(userInfo);
        return user;
    }

    public Optional<User> getUser(String username) {
        return userRepository.getUserByUsername(username);
    }

    public ModelAndView addUserToModel(ModelAndView model) {
        if (!user.isUnAuthenticatedUser()) {
            model.addObject("user", user);
        }
        return model;
    }

    public boolean changeAboutMe(String newAboutMe) {
        if (newAboutMe.length() < 500) {
            user.getUserInfo().setAbout(newAboutMe);
            userInfoRepository.merge(user.getUserInfo());
            return true;
        } else {
            return false;
        }
    }

    public boolean changeProgram(String newProgram) {
        if (newProgram.length() < 50) {
            user.getUserInfo().setProgram(newProgram);
            userInfoRepository.merge(user.getUserInfo());
            return true;
        } else {
            return false;
        }
    }

    public UserInfo getUserProfile(User userTarget) {
        if (userTarget.getUserInfo() != null) {
            return userTarget.getUserInfo();
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUser(userTarget);
            userInfo = userInfoRepository.saveAndFlush(userInfo);
            return userInfo;
        }
    }

    public boolean changePassword(String password, String newPassword) {
        CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder(user.getSalt());
        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            Optional<User> userTemp = userRepository.findById(user.getId());
            userTemp.get().setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.merge(userTemp.get());
            return true;
        } else {
            return false;
        }
    }
}
