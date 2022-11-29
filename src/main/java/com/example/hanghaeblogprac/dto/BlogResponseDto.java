package com.example.hanghaeblogprac.dto;

public class BlogResponseDto {
    private final String username;
    private final String contents;
    private final String author;

    public BlogResponseDto(String username, String contents, String author) {
        this.username = username;
        this.contents = contents;
        this.author = author;
    }
}
