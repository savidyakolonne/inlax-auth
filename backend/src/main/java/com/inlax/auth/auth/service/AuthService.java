package com.inlax.auth.auth.service;

import com.inlax.auth.auth.dto.RegisterRequest;
import com.inlax.auth.auth.dto.RegisterResponse;
import com.inlax.auth.user.entity.User;
import com.inlax.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .build();
    }
}
