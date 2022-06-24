package com.ot.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(formLogin -> formLogin
                // configure login
                .loginPage("/login")
                .usernameParameter("staffId")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureForwardUrl("/error")
                .permitAll()
        ).logout(logout -> logout
                // configure logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .invalidateHttpSession(true)
        )
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .mvcMatchers("resources/","/*/*.jpg","/*/*.css","/*/*.js").permitAll()
                .mvcMatchers("/h2-console").permitAll()
                .mvcMatchers("/overtime/").hasAuthority("overtime")
                .mvcMatchers("/position/").hasAuthority("setup")
                .mvcMatchers("/project/").hasAuthority("setup")
                .mvcMatchers("/role/").hasAuthority("setup")
                .mvcMatchers("/staff/").hasAuthority("setup")
                .mvcMatchers("/team/**").hasAuthority("setup")
                .mvcMatchers("/home/dsh003/**").hasAuthority("dsh003")
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}