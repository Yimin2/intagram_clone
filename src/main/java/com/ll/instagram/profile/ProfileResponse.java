package com.ll.instagram.profile;

import com.ll.instagram.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileResponse {
    private Long id;
    private String username;
    private String name;
    private String bio;
    private String profileImageUrl;

    private long postCount;
    private long followerCount;
    private long followingCount;

    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .bio(user.getBio())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .postCount(0)
                .followerCount(0)
                .followingCount(0)
                .build();
    }

    public static ProfileResponse from(User user, long postCount, long followerCount, long followingCount) {
        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .bio(user.getBio())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .postCount(postCount)
                .followerCount(followerCount)
                .followingCount(followingCount)
                .build();
    }
}
