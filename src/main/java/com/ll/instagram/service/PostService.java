package com.ll.instagram.service;

import com.ll.instagram.dto.request.PostCreateRequest;
import com.ll.instagram.dto.response.PostResponse;
import com.ll.instagram.entity.Post;

import java.util.List;

public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, Long userId);
    Post findById(Long postId);
    PostResponse getPost(Long postId);
    List<PostResponse> getAllPosts();
}