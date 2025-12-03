package com.ll.instagram.follow;

public interface FollowService {
    void toggleFollow(Long followerId, String followingUsername);
    boolean isFollowing(Long followerId, Long followingId);
}
