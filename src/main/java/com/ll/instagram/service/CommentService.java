package com.ll.instagram.service;

import com.ll.instagram.dto.request.CommentRequest;
import com.ll.instagram.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse create(Long postId, CommentRequest commentRequest, Long userId);
    List<CommentResponse> getComments(Long PostId);
}
