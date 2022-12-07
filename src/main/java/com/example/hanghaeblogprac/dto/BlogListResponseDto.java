package com.example.hanghaeblogprac.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BlogListResponseDto extends ResponseDto {
    List<BlogToDto> blogList = new ArrayList<>();     // 블로그 게시글 리스트 생성

    public BlogListResponseDto(String msg, int statusCode){        // ListResponse return시 msg + http Status Code 같이 반환
        super(msg, statusCode);
    }

    public void add(BlogToDto blogToDto){
        blogList.add(blogToDto);                      // ResponseDto에 담기위한 add function
    }

}
