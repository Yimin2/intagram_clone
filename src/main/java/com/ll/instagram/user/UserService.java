package com.ll.instagram.user;

import com.ll.instagram.auth.SignUpRequest;
import com.ll.instagram.profile.ProfileResponse;
import com.ll.instagram.profile.ProfileUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User register(SignUpRequest signUpRequest);
    boolean existsByUsername(String username);
    User findById(Long userId);
    ProfileResponse getProfile(String username);
    User findByUsername(String username);
    UserResponse getUserById(Long userId);
    void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg);
    List<UserResponse> searchUsers(String keyword);
}
