package com.ll.instagram.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Post> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"user"})
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);

    long countByUserId(Long userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findById(Long id);

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC ")
    Slice<Post> findFeedPostsByUserIds(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN FETCH p.user ORDER BY p.createdAt DESC")
    Slice<Post> findAllByWithUserPaging(Pageable pageable);
}