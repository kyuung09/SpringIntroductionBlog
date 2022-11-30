package com.example.hanghaeblogprac.entity;

import com.example.hanghaeblogprac.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    public Blog(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void deleteBlog(BlogRequestDto reqeustDto) {
        this.username = reqeustDto.getUsername();
        this.password = reqeustDto.getPassword();
    }

    public void update(BlogRequestDto reqeustDto) {
        this.title = reqeustDto.getTitle();
        this.username = reqeustDto.getUsername();
        this.contents = reqeustDto.getContents();
        this.password = reqeustDto.getPassword();
    }

}