package com.ll.instagram.comment;

import java.util.List;

public interface CommentService {
    CommentResponse create(Long postId, CommentRequest commentRequest, Long userId);
    List<CommentResponse> getComments(Long PostId);
}
