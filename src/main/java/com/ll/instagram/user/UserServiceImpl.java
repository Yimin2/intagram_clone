package com.ll.instagram.user;

import com.ll.instagram.auth.SignUpRequest;
import com.ll.instagram.common.FileService;
import com.ll.instagram.common.exception.BusinessException;
import com.ll.instagram.common.exception.ErrorCode;
import com.ll.instagram.follow.FollowRepository;
import com.ll.instagram.post.PostRepository;
import com.ll.instagram.profile.ProfileResponse;
import com.ll.instagram.profile.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public User register(SignUpRequest signUpRequest) {
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .username(signUpRequest.getUsername())
                .build();
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    @Override
    public ProfileResponse getProfile(String username) {
        User user = findByUsername(username);

        long postCount = postRepository.countByUserId(user.getId());
        long followerCount = followRepository.countByFollowingId(user.getId());
        long followingCount = followRepository.countByFollowerId(user.getId());

        return ProfileResponse.from(user, postCount, followerCount, followingCount);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg) {
        User user = findById(userId);
        if (profileImg != null && !profileImg.isEmpty()) {
            String savedFilename = fileService.saveFile(profileImg);
            String imageUrl = "/uploads/" + savedFilename;
            user.updateProfileImg(imageUrl);
        }
        user.updateProfile(profileUpdateRequest.getName(), profileUpdateRequest.getBio());
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return UserResponse.from(findById(userId));
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {
        return userRepository.searchByKeyword(keyword)
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}
