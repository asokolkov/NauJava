package ru.aleksey.NauJava.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{
    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests((authorization) -> authorization
                .requestMatchers("/").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/products").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/swagger-ui/**").hasRole("ADMIN")
                .requestMatchers("/api/products/*/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .logout((logout) -> logout.logoutUrl("/logout"))
            .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
