package com.example.hanghaeblogprac.repository;

import com.example.hanghaeblogprac.entity.Blog;
import com.example.hanghaeblogprac.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Comment Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlog(Blog blog);
}
