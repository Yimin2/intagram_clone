package com.ll.instagram.search;

import com.ll.instagram.post.PostResponse;
import com.ll.instagram.post.PostService;
import com.ll.instagram.user.UserResponse;
import com.ll.instagram.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchApiController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/users")
    public List<UserResponse> searchUsers(@RequestParam String q) {
        return userService.searchUsers(q.trim());
    }

    @GetMapping("/posts")
    public Slice<PostResponse> searchPosts(@RequestParam String q, @PageableDefault(size = 12)Pageable pageable) {
        return postService.searchPost(q.trim(), pageable);
    }
}
