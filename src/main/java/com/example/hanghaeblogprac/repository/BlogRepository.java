package com.example.hanghaeblogprac.repository;

import com.example.hanghaeblogprac.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//Blog Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByCreatedAtAsc();      // Select All 관련 리스트
}
