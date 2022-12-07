package com.example.hanghaeblogprac.entity;

import com.example.hanghaeblogprac.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name="Blog")
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id                                                         // primary_key
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // id값은 생성시 자동으로 생성됨
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "blog",cascade = CascadeType.REMOVE)          // blog(1) <-> comment(n) 관계
    List<Comment> comment = new ArrayList<>();                          // blog가 삭제될 경우 연관테이블인 comment내용도 삭제 됨

    public Blog(BlogRequestDto requestDto, String username, String password, Long userId) {
        this.title = requestDto.getTitle();                 // 사용자가 입력한 게시판 제목
        this.contents = requestDto.getContents();           // 사용자가 입력한 게시판 내용
        this.username = username;                           // 사용자 아이디 (user를 통해서 받아옴)
        this.password = password;                           // 사용자 패스워드 (..)
        this.userId = userId;                               // User Column Id
    }

    public void update(BlogRequestDto reqeustDto) {
        this.title = reqeustDto.getTitle();                 // 사용자가 입력한 게시펀 제목
        this.contents = reqeustDto.getContents();           // 사용자가 입력한 게시판 내용
    }

}