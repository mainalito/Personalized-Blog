package com.blog.services;

import com.blog.repository.LoginRepository;
import com.blog.repository.RegistrationRepository;
import com.blog.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class JPAUserDetailsService implements UserDetailsService  {
    private final RegistrationRepository registrationRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return registrationRepository.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found"));
    }
}
