package com.ll.instagram.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, MultipartFile image, Long userId);
    Post findById(Long postId);
    PostResponse getPost(Long postId);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByUsername(String username);
    List<PostResponse> getAllPostsWithStatus();
    Slice<PostResponse> getFeedPosts(Long userId, Pageable pageable);
    Slice<PostResponse> getAllPostsPaging(Pageable pageable);
}