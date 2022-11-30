package com.example.hanghaeblogprac.repository;

import com.example.hanghaeblogprac.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByCreatedAtAsc();      // Select All 관련 리스트

    Optional<Blog> findByIdAndPassword(Long id, String password);   // ID,PW 비교를 위한 Optional
}
