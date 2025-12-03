package com.ll.instagram.like;

public interface LikeService {
    void toggleLike(Long postId, Long userId);
    boolean isLiked(Long postId, Long userId);
    long getLikeCount(Long postId);
}
