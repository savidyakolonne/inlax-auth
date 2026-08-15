package com.inlax.auth.common.util;

import com.inlax.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameGenerator {

    private final UserRepository userRepository;

    public String generate(String fullName){

        String baseUsername = fullName
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "");

        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)){
            username = baseUsername + counter;
            counter++;
        }

        return username;
    }
}
