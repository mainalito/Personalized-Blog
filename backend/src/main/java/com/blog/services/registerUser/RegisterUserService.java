package com.blog.services.registerUser;

import com.blog.entities.RegisterUser;
import com.blog.repository.LoginRepository;
import com.blog.repository.RegistrationRepository;
import com.blog.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegisterUserService implements RegisterUserMethods{

    private final RegistrationRepository registrationRepository;

   private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUser saveUser(RegisterUser registerUser) {


            registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));

            return registrationRepository.save(registerUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (id != null) {
            registrationRepository.deleteById(id);
        }
    }

    @Override
    public RegisterUser getUser(Long id) {
        return null;
    }



}
