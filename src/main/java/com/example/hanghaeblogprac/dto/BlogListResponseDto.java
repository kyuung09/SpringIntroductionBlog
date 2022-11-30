package com.example.hanghaeblogprac.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BlogListResponseDto {
    List<BlogResponseDto> blogList = new ArrayList<>();     // 블로그 게시글 리스트 생성
    public void addBlog(BlogResponseDto blogResponseDto){
        blogList.add(blogResponseDto);                      // ResponseDto에 담기위한 add function
    }
}
