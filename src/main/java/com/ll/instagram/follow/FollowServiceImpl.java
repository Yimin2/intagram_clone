package com.ll.instagram.follow;

import com.ll.instagram.common.exception.BusinessException;
import com.ll.instagram.common.exception.ErrorCode;
import com.ll.instagram.user.UserService;
import com.ll.instagram.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void toggleFollow(Long followerId, String followingUsername) {
        User follower = userService.findById(followerId);
        User following = userService.findByUsername(followingUsername);

        Optional<Follow> existingFollow = followRepository.findByFollowerIdAndFollowingId(followerId,
                following.getId());
        if (follower.getId().equals(following.getId())) {
            throw new BusinessException(ErrorCode.SELF_FOLLOW);
        }
        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
        } else {
            Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();
            followRepository.save(follow);
        }
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(followerId,followingId);
    }
}
