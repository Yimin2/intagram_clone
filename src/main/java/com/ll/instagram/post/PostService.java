package com.ll.instagram.post;

import java.util.List;

public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, Long userId);
    Post findById(Long postId);
    PostResponse getPost(Long postId);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByUsername(String username);
    List<PostResponse> getAllPostsWithStatus();
}