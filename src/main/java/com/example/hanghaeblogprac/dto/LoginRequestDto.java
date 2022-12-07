package com.example.hanghaeblogprac.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequestDto {      // Login용 RequestDto
    private String username;        // 사용자 아이디
    private String password;        // 사용자 패스워드

}