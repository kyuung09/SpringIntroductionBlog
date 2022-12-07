package com.example.hanghaeblogprac.dto;

import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class BlogToDto {            // entity -> DTO
    private Long id;
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private ArrayList<Comment> commentArrayList;

    public BlogToDto(Blog blog) {
        this.id = blog.getId();                         // 게시물 ID
        this.title = blog.getTitle();                   // 제목
        this.content = blog.getContents();              // 컨텐츠 내용
        this.username = blog.getUsername();             // 사용자 이름
        this.createdAt = blog.getCreatedAt();           // 생성시간
        this.modifiedAt = blog.getModifiedAt();         // 수정시간
    }

    public BlogToDto(Blog blog, ArrayList<Comment> commentArrayList) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContents();
        this.username = blog.getUsername();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
        this.commentArrayList = (ArrayList<Comment>)commentArrayList.clone();
    }
}
