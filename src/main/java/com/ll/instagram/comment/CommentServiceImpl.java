package com.ll.instagram.comment;

import com.ll.instagram.post.Post;
import com.ll.instagram.post.PostService;
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
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponse create(Long postId, CommentRequest commentRequest, Long userId) {
        Post post = postService.findById(postId);
        User user = userService.findById(userId);

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .post(post)
                .user(user)
                .build();

        Comment saved = commentRepository.save(comment);
        return CommentResponse.from(saved);
    }

    @Override
    public List<CommentResponse> getComments(Long PostId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(PostId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }
}
