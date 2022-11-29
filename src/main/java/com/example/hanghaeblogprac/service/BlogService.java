package com.example.hanghaeblogprac.service;

import com.example.hanghaeblogprac.dto.BlogRequestDto;
import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public Blog createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return blog;
    }

    @Transactional
    public List<Blog> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Optional<Blog> getBlogsOne(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return blogRepository.findById(id);
    }


    @Transactional
    public long update(Long id, BlogRequestDto reqeustDto) {
        Blog blogNew = new Blog(reqeustDto);
        Blog blog = blogRepository.findByIdAndUsernameAndPassword(id, blogNew.getUsername(), blogNew.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        blog.update(reqeustDto);
        return blog.getId();
    }

//    @Transactional
//    public long update(Long id, BlogRequestDto reqeustDto) {
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
//        );
//        blog.update(reqeustDto);
//        return blog.getId();
//    }

    @Transactional
    public long deleteBlog(Long id, BlogRequestDto reqeustDto){
        Blog blogNew = new Blog(reqeustDto);
        Blog blog = blogRepository.findByIdAndPassword(id, blogNew.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        blogRepository.deleteById(id);
        blog.deleteBlog(reqeustDto);
        return blog.getId();
    }
}
//    @Transactional
//    public Long deleteBlog(Long id) {
//        blogRepository.deleteById(id);
//        return id;
//    }
//}

//    @Transactional
//    public boolean comparePassword(Long id, BlogRequestDto.BlogPasswordDto BlogPasswordDto){
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다"));
//        return Objects.equals(blog.getPassword(), BlogPasswordDto.getPassword());
//    }
//}
