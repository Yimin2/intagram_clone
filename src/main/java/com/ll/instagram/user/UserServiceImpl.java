package com.ll.instagram.user;

import com.ll.instagram.auth.SignUpRequest;
import com.ll.instagram.follow.FollowRepository;
import com.ll.instagram.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

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
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    @Override
    public ProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        long postCount = postRepository.countByUserId(user.getId());
        long followerCount = followRepository.countByFollowerId(user.getId());
        long followingCount = followRepository.countByFollowingId(user.getId());

        return ProfileResponse.from(user, postCount, followerCount, followingCount);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow();
    }
}
