package com.ll.instagram.service;

import com.ll.instagram.dto.request.SignUpRequest;
import com.ll.instagram.dto.response.ProfileResponse;
import com.ll.instagram.entity.User;

public interface UserService {
    User register(SignUpRequest signUpRequest);
    boolean existsByUsername(String username);
    User findById(Long userId);
    ProfileResponse getProfile(String username);
    User findByUsername(String username);
}
