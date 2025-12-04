package com.ll.instagram.post;


import com.ll.instagram.comment.CommentRepository;
import com.ll.instagram.like.LikeRepository;
import com.ll.instagram.user.UserService;
import com.ll.instagram.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public PostResponse create(PostCreateRequest postCreateRequest, Long userId) {
        User user = userService.findById(userId);

        Post post = Post.builder().content(postCreateRequest.getContent()).user(user).build();

        Post saved = postRepository.save(post);
        return PostResponse.from(saved);

    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = findById(postId);
        return PostResponse.from(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userService.findByUsername(username);

        return postRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getAllPostsWithStatus() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(post -> {
            long likeCount = likeRepository.countByPostId(post.getId());
            long commentCount = commentRepository.countByPostId(post.getId());
            return PostResponse.from(post, commentCount, likeCount);
        }).collect(Collectors.toList());
    }
}