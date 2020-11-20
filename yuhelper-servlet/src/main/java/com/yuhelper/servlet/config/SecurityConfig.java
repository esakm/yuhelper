package com.yuhelper.servlet.config;

import com.yuhelper.core.domain.security.custom.CustomDaoAuthenticationProvider;
import com.yuhelper.core.domain.security.repo.PersistentLoginRepository;
import com.yuhelper.core.model.Course;
import com.yuhelper.core.model.User;
import com.yuhelper.core.domain.security.service.UserSecurityService;
import com.yuhelper.core.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class  SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersistentLoginRepository persistentLoginRepositoryImpl;

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/assets/**",
            "/common/**",
            "/course",
            "/",
            "/home",
            "/home.html",
            "/courses/**",
            "/index",
            "/robots.txt",
            "/favicon.png",
            "/user/**",
            "/api/**"
    };

    public static final String[] AUTH_MATCHERS = {
            "/user/settings",
            "/user/change/**"
    };

    private static String[] NOT_AUTH_MATCHERS = {
            "/users/verify",
            "/users/add",
            "/users/resend",
            "/signup"
    };


    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public JavaMailSenderImpl MailSenderBean(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        Map<String, String> env = System.getenv();
        mailSender.setUsername("yuhelper.services@gmail.com");
        mailSender.setPassword(env.get("YUHELPER_PASSWORD"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public User UserBean(){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.toString().equals("anonymousUser")){
            return new User();
        }else{
            return (User) o;
        }
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Session fixation
        http.sessionManagement().sessionFixation().newSession();

        http
                .authorizeRequests()
                .antMatchers("/scraper").access("hasRole('admin')")
                .antMatchers(NOT_AUTH_MATCHERS).not().authenticated()
                .antMatchers(AUTH_MATCHERS).authenticated()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated();
        http.sessionManagement().maximumSessions(3);

        http
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll()
                .and().rememberMe().tokenRepository(persistentLoginRepositoryImpl)
                .tokenValiditySeconds(60 * 60 * 24 * 60)
                .userDetailsService(userSecurityService);

        // Auth provider
        http.authenticationProvider(authProvider());

        // Require SSL
        http.requiresChannel().anyRequest().requiresSecure();
    }

    public CustomDaoAuthenticationProvider authProvider(){
        CustomDaoAuthenticationProvider authProvider = new CustomDaoAuthenticationProvider();
        authProvider.setUserDetailsService(userSecurityService);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
