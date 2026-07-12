package com.security.SecurityApp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseDto {

    Integer id;
    String accessToken;
    String RefreshToken;
}
