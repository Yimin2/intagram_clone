package com.ll.instagram.post;


import com.ll.instagram.comment.CommentRepository;
import com.ll.instagram.common.FileService;
import com.ll.instagram.follow.FollowRepository;
import com.ll.instagram.like.LikeRepository;
import com.ll.instagram.user.UserService;
import com.ll.instagram.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final FileService fileService;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public PostResponse create(PostCreateRequest postCreateRequest, MultipartFile image, Long userId) {
        User user = userService.findById(userId);

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            String fileName = fileService.saveFile(image);
            imageUrl = "/uploads/" + fileName;
        }

        Post post = Post.builder()
                .content(postCreateRequest.getContent())
                .user(user)
                .imageUrl(imageUrl)
                .build();

        Post saved = postRepository.save(post);
        return PostResponse.from(saved);

    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow();
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
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> {
                    long likeCount = likeRepository.countByPostId(post.getId());
                    long commentCount = commentRepository.countByPostId(post.getId());
                    return PostResponse.from(post, commentCount, likeCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Slice<PostResponse> getFeedPosts(Long userId, Pageable pageable) {
        List<Long> followingIds = followRepository.findFollowingIdsByFollowerId(userId);
        Slice<Post> posts = postRepository.findFeedPostsByUserIds(followingIds, pageable);

        List<PostResponse> content = fromDto(posts);

        return new SliceImpl<>(content,pageable,posts.hasNext());
    }

    @Override
    public Slice<PostResponse> getAllPostsPaging(Pageable pageable) {
        Slice<Post> posts = postRepository.findAllByWithUserPaging(pageable);

        List<PostResponse> content = fromDto(posts);

        return new SliceImpl<>(content, pageable, posts.hasNext());
    }

    private List<PostResponse> fromDto(Slice<Post> posts) {
        List<PostResponse> content = posts.getContent()
                .stream()
                .map(post -> {
                    long likeCount = likeRepository.countByPostId(post.getId());
                    long commentCount = commentRepository.countByPostId(post.getId());

                    return  PostResponse.from(post, commentCount, likeCount);
                }).toList();
        return content;
    }
}