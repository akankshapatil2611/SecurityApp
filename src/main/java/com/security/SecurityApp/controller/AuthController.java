package com.security.SecurityApp.controller;

import com.security.SecurityApp.dto.LoginDto;
import com.security.SecurityApp.dto.LoginResponseDto;
import com.security.SecurityApp.dto.UserDto;
import com.security.SecurityApp.dto.signupDto;
import com.security.SecurityApp.entity.User;
import com.security.SecurityApp.service.AuthService;
import com.security.SecurityApp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signupUser(@RequestBody signupDto signupDto)
    {
        return new ResponseEntity<>(userService.signup(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto,
                                                  HttpServletResponse httpServletResponse)
    {
        LoginResponseDto login = authService.login(loginDto);

        // refresh
        Cookie cookie = new Cookie("refreshtoken", login.getRefreshToken());
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(login);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse httpServletResponse){
        Cookie cookie = new Cookie("refreshtoken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);           // delete cookie
        cookie.setPath("/");           // must match login path
        httpServletResponse.addCookie(cookie);

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
