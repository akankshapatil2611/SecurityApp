package com.security.SecurityApp.service;

import com.security.SecurityApp.dto.LoginDto;
import com.security.SecurityApp.dto.LoginResponseDto;
import com.security.SecurityApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto)
    {
        try {
            Authentication authentication = authenticationConfiguration
                    .getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    ));
            User user = (User) authentication.getPrincipal();
            return sessionService.createSession(user);
        }
        catch (Exception e)
        {
            throw new BadCredentialsException("Invalid Email and Password");
        }
    }

}
