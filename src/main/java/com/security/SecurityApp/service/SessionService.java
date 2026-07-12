package com.security.SecurityApp.service;

import com.security.SecurityApp.dto.LoginDto;
import com.security.SecurityApp.dto.LoginResponseDto;
import com.security.SecurityApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final JwtService jwtService;

    public LoginResponseDto createSession(User user)
    {
        String accesstoken = jwtService.generetedAccessToken(user);
        String refreshToken = jwtService.generetedRefreshToken(user);
        return new LoginResponseDto(user.getId(), accesstoken, refreshToken);
    }
}
