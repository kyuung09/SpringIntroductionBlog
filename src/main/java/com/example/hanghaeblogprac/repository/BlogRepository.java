package com.example.hanghaeblogprac.repository;

import com.example.hanghaeblogprac.entity.Blog;
import org.junit.jupiter.api.AfterEach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();
    Optional<Blog> findByIdAndUsernameAndPassword(Long id, String Username, String password);

    Optional<Blog> findByIdAndPassword(Long id, String password);

}
