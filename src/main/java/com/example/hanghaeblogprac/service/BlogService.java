package com.example.hanghaeblogprac.service;

import com.example.hanghaeblogprac.dto.BlogListResponseDto;
import com.example.hanghaeblogprac.dto.BlogRequestDto;
import com.example.hanghaeblogprac.dto.BlogResponseDto;
import com.example.hanghaeblogprac.dto.ResponseDto;
import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    // Blog posting Create Function
    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);               // DTO를 Entity로 변환
        blogRepository.save(blog);                      // 변환된 객체를 DB에 저장
        return new BlogResponseDto(blog);               // Entity를 responseDTO에 담아서 반환함
    }

    // Get Blog from Database(all)
    public BlogListResponseDto getBlogs() {
        BlogListResponseDto blogListResponseDto = new BlogListResponseDto();
        List<Blog> blogs = blogRepository.findAllByOrderByCreatedAtAsc();       // DB에서 전체 데이터를 조회해서하는데 생성시간 기준 역순으로 조회
        for (Blog blog : blogs) {
            blogListResponseDto.addBlog(new BlogResponseDto(blog));             // 조회한 데이터를 responseDto에 넣어서 반환
        }
        return blogListResponseDto;
    }

    // Get Blog from Database(one)
    public BlogResponseDto getBlogsOne(Long id) {
        Blog blog = checkBlogs(blogRepository, id);         //  checkBlog Function을 통해서 일치하는 ID 가 있는지 확인, 없으면 오류 반환
        return new BlogResponseDto(blog);                   //  입력받은 Id로 DB select하여 반환
    }

    // Update DB Function
    public BlogResponseDto update(Long id, BlogRequestDto reqeustDto) {
        Blog blog = blogRepository.findByIdAndPassword(id, reqeustDto.getPassword()).orElseThrow(       // ID,PW 비교하여 일치하는지 확인
                () -> new IllegalArgumentException("아이디가 존재하지 않거나, 패스워드가 일치하지 않습니다.")
        );
        blog.update(reqeustDto);                                                                        // request Dto내용으로 Update
        Blog saveBlog = blogRepository.save(blog);                                                      // 업데이트한 내용 저장 (Transection 어노테이션을 제거하여 변경된 상태에서 커밋해주는 로직이 필요함)
        return new BlogResponseDto(saveBlog);
    }

    // Delete DB Function
    public ResponseDto deleteBlog(Long id, BlogRequestDto reqeustDto) {
        blogRepository.findByIdAndPassword(id, reqeustDto.getPassword()).orElseThrow(                     // Delete용 ResponseDto 생성
                () -> new IllegalArgumentException("패스워드가 틀렸습니다.")                                    // 비밀번호 일치하는지 확인
        );
        blogRepository.deleteById(id);                                                                     // 해당 ID를 DB에서 삭제
        return new ResponseDto("삭제가 완료되었습니다.");
    }

    private Blog checkBlogs(BlogRepository blogRepository, Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new RuntimeException("아이디를 찾을 수 없습니다.")
        );
    }
}