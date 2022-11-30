package com.example.hanghaeblogprac.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;           // 제목
    private String username;        // 사용자 이름
    private String contents;        // 컨텐츠명
    private String password;        // 패스워드
}