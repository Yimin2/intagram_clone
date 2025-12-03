package com.ll.instagram.service;

import com.ll.instagram.dto.request.SignUpRequest;
import com.ll.instagram.dto.response.ProfileResponse;
import com.ll.instagram.entity.User;
import com.ll.instagram.repository.PostRepository;
import com.ll.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

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

        return ProfileResponse.from(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow();
    }
}
