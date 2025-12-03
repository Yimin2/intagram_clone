package com.ll.instagram.repository;

import com.ll.instagram.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
    Boolean existsByPostIdAndUserId(Long postId, Long userId);
    Long countByPostId(Long postId);
}
