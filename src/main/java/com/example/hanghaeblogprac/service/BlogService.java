package com.example.hanghaeblogprac.service;

import com.example.hanghaeblogprac.dto.BlogListResponseDto;
import com.example.hanghaeblogprac.dto.BlogRequestDto;
import com.example.hanghaeblogprac.dto.BlogResponseDto;
import com.example.hanghaeblogprac.dto.ResponseDto;
import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogListResponseDto getBlogs() {
        BlogListResponseDto blogListResponseDto = new BlogListResponseDto();
        List<Blog> blogs = blogRepository.findAllByOrderByCreatedAtAsc();
        for (Blog blog : blogs) {
            blogListResponseDto.addBlog(new BlogResponseDto(blog));
        }
        return blogListResponseDto;
    }


    @Transactional
    public BlogResponseDto getBlogsOne(Long id) {
        Blog blog = checkBlogs(blogRepository, id);
        return new BlogResponseDto(blog);
    }


    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto reqeustDto) {
        Blog blog = blogRepository.findByIdAndPassword(id, reqeustDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않거나, 패스워드가 일치하지 않습니다.")
        );
        blog.update(reqeustDto);
        return new BlogResponseDto(blog);
    }

    @Transactional
    public ResponseDto deleteBlog(Long id, BlogRequestDto reqeustDto) {
        blogRepository.findByIdAndPassword(id, reqeustDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("패스워드가 틀렸습니다.")
        );
        blogRepository.deleteById(id);
        return new ResponseDto("삭제가 완료되었습니다.");
    }


    private Blog checkBlogs(BlogRepository blogRepository, Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new RuntimeException("아이디를 찾을 수 없습니다.")
        );
    }
}