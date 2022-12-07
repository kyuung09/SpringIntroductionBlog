package com.example.hanghaeblogprac.dto;

import com.example.hanghaeblogprac.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Getter
@NoArgsConstructor
@Configuration
public class CommentResponseDto extends ResponseDto{ // 응답용 Dto 생성

    CommentToDto commentOne; // CommentToDto 연결

    // 생성자(mgs+Statuscode)
    public CommentResponseDto(String msg, int statusCode) {
        super(msg, statusCode);
    }

    // 생성자(mgs+Statuscode+Entity)
    public CommentResponseDto(String msg, int statusCode, Comment comment){
        super(msg, statusCode);
        this.commentOne = new CommentToDto(comment);
    }
}
