package com.inlax.auth.auth.service;

import com.inlax.auth.user.entity.RefreshToken;
import com.inlax.auth.user.repository.RefreshTokenRepository;
import com.inlax.auth.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public RefreshToken createRefreshToken(User user){

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(
                        LocalDateTime.now()
                                .plusSeconds(refreshExpiration / 1000)
                )
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token){

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh token not found")
                );
    }

    public boolean isExpired(RefreshToken refreshToken){

        return refreshToken.getExpiresAt()
                .isBefore(LocalDateTime.now());
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken){

        if (isExpired(refreshToken)){

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
