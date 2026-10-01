package com.stock.portfolioalertapp.service;

import com.stock.portfolioalertapp.dto.AuthResponse;
import com.stock.portfolioalertapp.dto.LoginRequest;
import com.stock.portfolioalertapp.dto.RegisterRequest;
import com.stock.portfolioalertapp.dto.UserResponse;
import com.stock.portfolioalertapp.entity.User;
import com.stock.portfolioalertapp.repository.UserRepository;
import com.stock.portfolioalertapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegistrationEmailPublisher registrationEmailPublisher;

    public UserResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);

        // Registration email is still sent, but no JWT is generated here.
        registrationEmailPublisher.publish(saved);

        return new UserResponse(saved);
    }

    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return response(jwtService.generateToken(email), user);
    }

    private AuthResponse response(String token, User user) {
        return new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
