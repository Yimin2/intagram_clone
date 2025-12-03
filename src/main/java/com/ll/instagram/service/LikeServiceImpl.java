package com.ll.instagram.service;

import com.ll.instagram.entity.Like;
import com.ll.instagram.entity.Post;
import com.ll.instagram.entity.User;
import com.ll.instagram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

    private final PostService postService;
    private final UserService userService;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void toggleLike(Long postId, Long userId) {
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);

        if(existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
        Post post = postService.findById(postId);
        User user = userService.findById(userId);

        Like like = Like.builder()
                .post(post)
                .user(user)
                .build();
        likeRepository.save(like);
        }
    }

    @Override
    public boolean isLiked(Long postId, Long userId) {
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
