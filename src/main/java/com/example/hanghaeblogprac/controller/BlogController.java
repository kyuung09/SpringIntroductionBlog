package com.example.hanghaeblogprac.controller;

import com.example.hanghaeblogprac.dto.BlogRequestDto;
import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;


    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/blogs")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto){
        return blogService.createBlog(requestDto);
    }

    @GetMapping("/api/blogs")
    public List<Blog> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/api/blogs/{id}")
    public Optional<Blog> getBlogsOne(@PathVariable Long id, BlogRequestDto requestDto) {
        return blogService.getBlogsOne(id, requestDto);
    }

    @PutMapping("/api/blogs/{id}")
    public long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto reqeustDto){
        return blogService.update(id, reqeustDto);
    }

    @DeleteMapping("/api/blogs/{id}")
    public Long deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        return blogService.deleteBlog(id, requestDto);
    }


//    @PostMapping("/api/blogs/{id}")
//    public boolean comparePassword (@PathVariable Long id,@RequestBody BlogRequestDto.BlogPasswordDto BlogPasswordDto) {
//        return blogService.comparePassword(id, BlogPasswordDto);
//    }

}
