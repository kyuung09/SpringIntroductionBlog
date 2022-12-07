package com.example.hanghaeblogprac.controller;

import com.example.hanghaeblogprac.dto.BlogListResponseDto;
import com.example.hanghaeblogprac.dto.BlogRequestDto;
import com.example.hanghaeblogprac.dto.BlogResponseDto;
import com.example.hanghaeblogprac.dto.ResponseDto;
import com.example.hanghaeblogprac.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // Blog posting DB Save
    @PostMapping("/api/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        // try-catch문을 통한 예외처리

        try {
            return blogService.createBlog(requestDto, request);                                 // 예외가 발생할 부분을 try으로 묶어주고
        } catch (Exception e) {                                                                 // 예외가 발생하면 catch(Exception e)가 실행된다
            return new BlogResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());         // 예외 발생시 에러내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }

    // DB Select all
    @GetMapping("/api/blogs")
    public BlogListResponseDto getBlogs() {
        return blogService.getBlogs();
    }

    // DB Select One
    @GetMapping("/api/blogs/{id}")
    public BlogResponseDto getBlogsOne(@PathVariable Long id) {
        return blogService.getBlogsOne(id);
    }

    // Blog posting DB Update
    @PutMapping("/api/blogs/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto reqeustDto, HttpServletRequest request) {
        try {
            return blogService.update(id, reqeustDto, request);
        } catch (Exception e) {
            return new  BlogResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());        // 예외 발생시 에러내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }

    // Blog posting DB Delete
    @DeleteMapping("/api/blogs/{id}")
    public ResponseDto deleteBlog(@PathVariable Long id, HttpServletRequest request) {
        try {
            return blogService.deleteBlog(id, request);
        } catch (Exception e) {
            return new  ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());        // 예외 발생시 에러내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }
}
