package com.ll.instagram.user;

import com.ll.instagram.auth.SignUpRequest;
import com.ll.instagram.profile.ProfileResponse;

public interface UserService {
    User register(SignUpRequest signUpRequest);
    boolean existsByUsername(String username);
    User findById(Long userId);
    ProfileResponse getProfile(String username);
    User findByUsername(String username);
    UserResponse getUserById(Long userId);
}
