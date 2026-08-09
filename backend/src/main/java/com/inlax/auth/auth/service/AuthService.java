package com.inlax.auth.auth.service;

import com.inlax.auth.auth.dto.LoginRequest;
import com.inlax.auth.auth.dto.LoginResponse;
import com.inlax.auth.auth.dto.RegisterRequest;
import com.inlax.auth.auth.dto.RegisterResponse;
import com.inlax.auth.common.util.UsernameGenerator;
import com.inlax.auth.exception.EmailAlreadyExistsException;
import com.inlax.auth.security.jwt.JwtService;
import com.inlax.auth.user.entity.Role;
import com.inlax.auth.user.entity.User;
import com.inlax.auth.user.repository.UserRepository;
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
    private final UsernameGenerator usernameGenerator;

    public RegisterResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String username = usernameGenerator.generate(request.getFullName());

        User user = User.builder()
                .fullName(request.getFullName())
                .username(username)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    public LoginResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        String token = jwtService.generateToken(user.getUsername());

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}
