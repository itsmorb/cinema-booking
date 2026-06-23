package org.example.cinemabooking.service;

import lombok.RequiredArgsConstructor;
import org.example.cinemabooking.dto.request.LoginRequest;
import org.example.cinemabooking.dto.request.RegisterRequest;
import org.example.cinemabooking.dto.response.JwtResponse;
import org.example.cinemabooking.entity.User;
import org.example.cinemabooking.repository.UserRepository;
import org.example.cinemabooking.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return "Username is already in use";
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return "Email is already in use";
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(User.Role.USER);

        userRepository.save(user);
        return "User created";
    }

    public JwtResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return new JwtResponse(token);
    }
}
