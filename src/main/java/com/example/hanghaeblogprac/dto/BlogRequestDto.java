package com.example.hanghaeblogprac.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;
    private String username;
    private String contents;
    private String password;
}