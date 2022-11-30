package com.example.hanghaeblogprac.dto;

import com.example.hanghaeblogprac.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Configuration
public class BlogResponseDto {  // 응답용 Dto
    private Long id;    // 아이디
    private String title;   // 제목
    private String username;    // 사용자 이름
    private String contents;    // 컨텐츠명
    private LocalDateTime createdAt;    // 생성일자
    private LocalDateTime modifiedAt;   // 수정일자

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();                     //ID
        this.title = blog.getTitle();               //제목
        this.username = blog.getUsername();         //사용자이름
        this.contents = blog.getContents();         //컨텐츠명
        this.createdAt = blog.getCreatedAt();       //생성일자
        this.modifiedAt = blog.getModifiedAt();     //수정일자
    }
}