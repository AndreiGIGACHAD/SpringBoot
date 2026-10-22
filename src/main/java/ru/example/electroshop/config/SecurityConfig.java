package ru.example.electroshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        return http.authorizeHttpRequests(a->a.requestMatchers("/login","/register","/css/**","/error","/actuator/health").permitAll().requestMatchers("/admin/**","/h2-console/**").hasRole("ADMIN").anyRequest().authenticated()).formLogin(f->f.loginPage("/login").defaultSuccessUrl("/",true).permitAll()).logout(l->l.logoutSuccessUrl("/login?logout").permitAll()).headers(h->h.frameOptions(f->f.sameOrigin())).build();
    }
}
