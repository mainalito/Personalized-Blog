package com.blog.config;

import com.blog.services.JPAUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private static final String[] WHITELIST = {
            "/api/auth/**",
            "/cloud/upload"
    };

    private final JPAUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security
                .authorizeRequests()
                .mvcMatchers(WHITELIST).permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/register/*").authenticated()
                .mvcMatchers(HttpMethod.POST, "api/posts/save").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api/posts/*", "/api/posts/").permitAll()
                .anyRequest().authenticated()
                .and()
                // form login
                .csrf().disable()

                .authenticationProvider(authenticationProvider())
                .headers().frameOptions().sameOrigin()
                .and()
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies()
                )
                .httpBasic().and()
                .build();
    }
}

