package com.example.hanghaeblogprac.controller;

import com.example.hanghaeblogprac.dto.BlogListResponseDto;
import com.example.hanghaeblogprac.dto.BlogRequestDto;
import com.example.hanghaeblogprac.dto.BlogResponseDto;
import com.example.hanghaeblogprac.dto.ResponseDto;
import com.example.hanghaeblogprac.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // Blog posting DB Save
    @PostMapping("/api/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
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
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto reqeustDto) {
        return blogService.update(id, reqeustDto);
    }

    // Blog posting DB Delete
    @DeleteMapping("/api/blogs/{id}")
    public ResponseDto deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.deleteBlog(id, requestDto);
    }
}
