package com.ll.instagram.dto.response;

import com.ll.instagram.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileResponse {
    private Long id;
    private String username;
    private String name;
    private String bio;

    private long postCount;
    private long followerCount;
    private long followingCount;

    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .name(user.getName())
                .postCount(0)
                .followerCount(0)
                .followingCount(0)
                .build();
    }
}
