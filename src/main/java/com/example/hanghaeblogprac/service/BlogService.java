package com.example.hanghaeblogprac.service;

import com.example.hanghaeblogprac.dto.*;
import com.example.hanghaeblogprac.entity.Comment;
import com.example.hanghaeblogprac.entity.UserRoleEnum;
import com.example.hanghaeblogprac.jwt.JwtUtil;
import com.example.hanghaeblogprac.repository.CommentRepository;
import com.example.hanghaeblogprac.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.entity.User;
import com.example.hanghaeblogprac.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    // Blog posting Create Function
    public BlogResponseDto createBlog(BlogRequestDto requestDto, HttpServletRequest request) {


        String token = jwtUtil.resolveToken(request);            // Request(Token) -> BlogService
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {                                 // Token에서 사용자 정보 추출
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");      // Token이 유효하지 않을 경우 Exception throw
            }


            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(     // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")              // 만약 토큰과 일치하는 사용자가 없을 경우 Exception throw
            );


            Blog blog = blogRepository.saveAndFlush(new Blog(requestDto, user.getUsername(), user.getPassword(), user.getId()));    // DB에 입력받은 내용 + User에서 추출한 name/password/id 저장


            return new BlogResponseDto("success", HttpStatus.OK.value(), blog);          //  DB에 내용 저장 성공시 성공메세지, Http Status Code, Entity에 저장된 정보 반환
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");                       // 만약 토큰이 없을 경우(38번쨰 라인 else) Exception throw
        }
    }

    // Get Blog from Database(all)
    public BlogListResponseDto getBlogs() {

        BlogListResponseDto blogListResponseDto = new BlogListResponseDto("success", HttpStatus.OK.value());   // 조회한 엔티티 결과와 status, msg 같이 반환

        List<Blog> blogs = blogRepository.findAllByOrderByCreatedAtAsc();                                           // DB에서 전체 데이터를 조회해서하는데 생성시간 기준 역순으로 조회

        for (Blog blog : blogs) {                                                                                   // blog size 만큼 반복
            List<Comment> comments = commentRepository.findAllByBlog(blog);                                         // comment List에 DB에서 조회한 내용 저장

            if (comments.isEmpty()) {                                                                               // 만약 comments가 비어있다면 == comment의 blog_id와 일치하는 블로그 게시판 내용이 없으면
                blogListResponseDto.add(new BlogToDto(blog));                                                      // 블로그 게시판 내용만 blogList에 추가
            } else {                                                                                                 // 매핑되는 댓글이 있다면
                blogListResponseDto.add(new BlogToDto(blog, (ArrayList<Comment>) comments));                         // 게시판 내용 + 매핑되는 Comment 내용 추가
            }

        }
        return blogListResponseDto;                                                                              // List 조회 결과 반환
    }

    // Get Blog from Database(one)
    public BlogResponseDto getBlogsOne(Long id) {
        Blog blog = checkBlogs(blogRepository, id);                                                 //  checkBlog Function을 통해서 일치하는 ID 가 있는지 확인, 없으면 오류 반환
        List<Comment> comments = commentRepository.findAllByBlog(blog);                             // comment List에 DB에서 조회한 내용 저장
        if (comments.isEmpty()) {                                                                    // 만약 comments가 비어있다면 == comment의 blog_id와 일치하는 블로그 게시판 내용이 없으면
            return new BlogResponseDto("success", HttpStatus.OK.value(), blog);                // 선택된 블로그 게시판 내용만 반환
        } else {
            return new BlogResponseDto("fail", HttpStatus.BAD_REQUEST.value(), blog, (ArrayList<Comment>) comments);   // 게시판 내용 + 매핑되는 Comment 내용 반환
        }
    }

    // Update DB Function
    public BlogResponseDto update(Long id, BlogRequestDto reqeustDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);           // Request(Token) -> BlogService
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );


            UserRoleEnum userRoleEnum = user.getRole();                     // 사용자의 User Type(User, Admin) 값 받아오기
            Blog blog;                                                      // Blog Entity 연결

            if (userRoleEnum == UserRoleEnum.USER) {                                // 사용자의 권한이 일반 사용자(User)일 경우

                blog = blogRepository.findById(id).orElseThrow(                         // 입력받은 게시판 Id를 DB에서 조회했을 때 일치하는 값이 없으면
                        () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")      // nullPointException Throw
                );

                if (blog.getId().equals(user.getId())) {                    // blog에 저장된 User ID와 업데이트를 시도하는 User의 ID가 같을 경우
                    blog.update(reqeustDto);                                // 업데이트 처리
                } else {
                    throw new IllegalArgumentException("게시물 수정 권한이 없습니다.");      // blog에 저장된 User ID와 업데이트를 시도하는 User의 ID가 다를 경우 Excption Throw
                }
            } else {                                                                  // User 권한이 Admin일 경우 모든 내용 수정 가능
                blog = blogRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
                );
                blog.update(reqeustDto);                                                                    // 업데이트 처리
            }
            Blog saveBlog = blogRepository.save(blog);                                                      // 업데이트한 내용 저장 (Transection 어노테이션을 제거하여 변경된 상태에서 커밋해주는 로직이 필요함)
            return new BlogResponseDto("suceess", HttpStatus.OK.value(), saveBlog);
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");                                      // 만약 토큰이 없을 경우 Exception
        }
    }

    // Delete DB Function
    public ResponseDto deleteBlog(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            Blog blog;

            if (userRoleEnum == UserRoleEnum.USER) {
                blog = blogRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
                );

                if (blog.getUserId().equals(user.getId())) {
                    blogRepository.deleteById(id);
                } else {
                    throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
                }
            } else {                                                                                          // User 권한이 Admin일 경우 모든 내용 수정 가능
                blog = blogRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
                );
                blogRepository.deleteById(id);                                                                // 삭제 처리
            }
//            String loginUserName = user.getUsername();
//            if (!blog.getUsername().equals(loginUserName)) {
//                throw new IllegalArgumentException("회원님의 게시글이 아닙니다!");
//            }
            return new ResponseDto("success", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }

    private Blog checkBlogs(BlogRepository blogRepository, Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new RuntimeException("아이디를 찾을 수 없습니다.")
        );
    }
}

