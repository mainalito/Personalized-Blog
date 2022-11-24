package com.blog.controllers;

import com.blog.Responses.ResponseHandler;
import com.blog.dtos.LoginDto;
import com.blog.dtos.RegisterUserDto;
import com.blog.dtos.ResponseUserDto;
import com.blog.dtos.SignUpDto;
import com.blog.entities.RegisterUser;
import com.blog.entities.Role;
import com.blog.repository.RegistrationRepository;
import com.blog.services.registerUser.RegisterUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
    //TODO:CREATE A FORM TO UPDATE USER DETAILS.
    private final AuthenticationManager authenticationManager;
    private final RegisterUserService registerUserService;
    private final RegistrationRepository registrationRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto signUpDto) {
        ResponseUserDto userDto = new ResponseUserDto(signUpDto.username(), signUpDto.email(), signUpDto.imageUrl());
        // add check for username exists in a DB
        if (registrationRepository.existsByUsername(signUpDto.username())) {
            //return new ResponseEntity<>();
            return ResponseHandler.generateResponse("Username is already taken!", HttpStatus.BAD_REQUEST, userDto);
        }

        // add check for email exists in DB
        if (registrationRepository.existsByEmail(signUpDto.email())) {
            //return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            return ResponseHandler.generateResponse("Email is already taken!", HttpStatus.BAD_REQUEST, userDto);
        }

        // create REGISTER USER object
        RegisterUser user = new RegisterUser();
        user.setUsername(signUpDto.username());
        user.setEmail(signUpDto.email());
        user.setPassword(passwordEncoder.encode(signUpDto.password()));
        user.setImageUrl(signUpDto.imageUrl());
        user.setRole(Role.USER);
        RegisterUser save = registrationRepository.save(user);
        SignUpDto responseUserDto = new SignUpDto(save.getId(), save.getUsername(), save.getEmail(), save.getImageUrl());

        return ResponseHandler.generateResponse("User registered successfully", HttpStatus.CREATED, responseUserDto);

    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(
            @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<RegisterUser> userOptional = registrationRepository.findByUsername(authentication.getName());
            if (userOptional.isPresent()) {
                RegisterUser registerUser = userOptional.get();
                registerUser.setIsLoggedin(true);
                RegisterUser user = registrationRepository.save(registerUser);
                SignUpDto userDto = new SignUpDto(user.getId(), user.getUsername(), user.getEmail(), user.getImageUrl());
                return ResponseHandler.generateResponse("Logged in successfully", HttpStatus.OK, userDto);
            }
            return ResponseHandler.generateResponse("Invalid username or password", HttpStatus.OK, null);
        } catch (BadCredentialsException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestBody LoginDto logoutDto, HttpSession session) {
//        try {
//
//            Optional<RegisterUser> optionalRegisterUser = registrationRepository.findByUsername(logoutDto.username());
//            if (optionalRegisterUser.isPresent()) {
//                RegisterUser registerUser = optionalRegisterUser.get();
//                registerUser.setIsLoggedin(false);
//                registrationRepository.save(registerUser);
//                return ResponseHandler.generateResponse("Logged out successful!", HttpStatus.OK, null);
//            }
//            return ResponseHandler.generateResponse("Unsuccessful logout", HttpStatus.BAD_REQUEST, null);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse("Error!", HttpStatus.BAD_REQUEST, null);
//        }
//
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        registerUserService.deleteUser(id);
        return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
    }
}
