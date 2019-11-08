package com.yuhelper.core.service;

import com.yuhelper.core.domain.security.custom.CustomPasswordEncoder;
import com.yuhelper.core.domain.security.model.Role;
import com.yuhelper.core.domain.security.model.SignUpToken;
import com.yuhelper.core.domain.security.model.UserRole;
import com.yuhelper.core.domain.security.repo.RoleRepository;
import com.yuhelper.core.domain.security.repo.SignUpTokenRepository;
import com.yuhelper.core.domain.security.repo.UserRoleRepository;
import com.yuhelper.core.domain.security.service.UserSecurityService;
import com.yuhelper.core.model.User;
import com.yuhelper.core.repo.UserRepository;
import com.yuhelper.core.utils.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    SignUpTokenRepository signUpTokenRepo;

    public boolean checkNewUsernameAndEmail(String username, String email){
        try{
            UserDetails usernameCheck = userSecurityService.loadUserByUsername(username);
            return false;
        }catch(UsernameNotFoundException e){
            try{
                UserDetails emailCheck = userSecurityService.loadUserByEmail(email);
                return false;
            }catch(UsernameNotFoundException e1){
                return true;
            }
        }
    }

    // CHECK IF 2 PASSWORDS ARE EQUAL
    public boolean checkPassword(String p1, String p2){
        return p1.equals(p2);
    }

    public User createUser(String username, String email, String password){
        CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
        String newSalt = passwordEncoder.getSalt();
        User user = new User(username, email, passwordEncoder.encode(password), newSalt);
        user = userRepository.saveAndFlush(user);
        UserRole userRole = new UserRole(user, roleRepository.findByName("ROLE_user"));
        userRoleRepository.saveAndFlush(userRole);
        SecureRandom random = new SecureRandom();
        byte[] newToken = new byte[32];
        random.nextBytes(newToken);
        SignUpToken signUpToken = new SignUpToken(user, StringConverter.bytesToHex(newToken));
        signUpTokenRepo.saveAndFlush(signUpToken);
        user.setSignUpToken(signUpToken);
        return user;
    }

}
