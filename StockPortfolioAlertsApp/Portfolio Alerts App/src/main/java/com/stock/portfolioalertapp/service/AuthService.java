package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.dto.AuthResponse;
import com.stock.portfolioalertapp.dto.LoginRequest;
import com.stock.portfolioalertapp.dto.RegisterRequest;
import com.stock.portfolioalertapp.dto.UserResponse;
import com.stock.portfolioalertapp.entity.User;
import com.stock.portfolioalertapp.repository.UserRepository;
import com.stock.portfolioalertapp.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }


    public UserResponse register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        validatePassword(request.getPassword());

        String encryptedPassword =
                passwordEncoder.encode(
                        request.getPassword()
                );

        User user = new User();

        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(encryptedPassword);

        User savedUser =
                userRepository.save(user);

        return new UserResponse(savedUser);
    }


    public AuthResponse login(LoginRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(email);

        String token =
                jwtService.generateToken(userDetails);

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return new AuthResponse(
                "Login successful",
                token,
                new UserResponse(user)
        );
    }


    private void validatePassword(String password) {


        String passwordRegex =
                "^(?=.*[a-z])"
                        + "(?=.*[A-Z])"
                        + "(?=.*\\d)"
                        + "(?=.*[@#$%^*_\\-])"
                        + ".{8,}$";

        if (!password.matches(passwordRegex)) {

            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters, "
                            + "one uppercase letter, "
                            + "one lowercase letter, "
                            + "one number, "
                            + "and one special character "
                            + "from [@, #, $, %, ^, *, -, _]"
            );
        }
    }
}