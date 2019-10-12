package by.attrade.config;

import by.attrade.domain.User;
import by.attrade.provider.AuthProvider;
import by.attrade.service.UserService;
import by.attrade.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthProvider authProvider;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserService userService) {
        return map -> {
            String sub = (String) map.get("sub");
            User userFromDB = userService.findBySub(sub);
            User user = Optional.ofNullable(userFromDB).orElseGet(() ->
                    User.builder()
                            .username(String.valueOf(map.get("email")))
                            .password("1Aa".concat(UUID.randomUUID().toString()))
                            .sub(sub)
                            .email(String.valueOf(map.get("email")))
                            .active((Boolean) map.get("email_verified"))
                            .roles(Collections.singleton(Role.USER))
                            .build()
            );
            log.debug("Try save new user: {}",user);
            return userService.save(user);
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/",
                        "/registration",
                        "/static/**",
                        "/activate/*",
                        "/pre-login",
                        "/product",
                        "/test").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .rememberMe()
                .and()
                .logout()
                .permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .and()
                .csrf()
                .ignoringAntMatchers("/message/**")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
        auth.authenticationProvider(authProvider);
    }

}