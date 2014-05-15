package com.github.lemniscate.stack.boot.config;

import com.github.lemniscate.stack.boot.svc.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;

/**
 * @Author dave 2/8/14 11:17 AM
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private UserDetailsService userService;

    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(new BcryptPasswordEncoder());

        auth.authenticationProvider(provider);
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FormLoginConfigurer<HttpSecurity> form = http.formLogin();
        form.loginProcessingUrl("/doLogin");
        form.loginPage("/login");

        // don't add ROLE_ to the role...
        http.authorizeRequests()
                .antMatchers("/app/**").hasRole("DEFAULT");

        http.csrf().disable();
    }

    @Bean
    public SecurityProperties securityProperties() {
        SecurityProperties security = new SecurityProperties();
        security.setSessions(SessionCreationPolicy.ALWAYS);

        SecurityProperties.Basic basic = security.getBasic();
//        basic.setPath(""); // empty so home page is unsecured
        return security;
    }

    private class BcryptPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
        }
    }

}
