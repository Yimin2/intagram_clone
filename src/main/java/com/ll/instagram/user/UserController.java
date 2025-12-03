package com.ll.instagram.user;

import com.ll.instagram.post.PostResponse;
import com.ll.instagram.common.security.CustomUserDetails;
import com.ll.instagram.follow.FollowService;
import com.ll.instagram.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;

    @GetMapping("/{username}")
    public String profile(
            @PathVariable String username,
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ProfileResponse profile = userService.getProfile(username);
        List<PostResponse> posts = postService.getPostsByUsername(username);

        model.addAttribute("profile", profile);
        model.addAttribute("posts", posts);

        boolean isFollowing = followService.isFollowing(userDetails.getId(), profile.getId());
        model.addAttribute("isFollowing", isFollowing);

        return "user/profile";
    }

    @PostMapping("/{username}/follow")
    public String toggleFollow(
            @PathVariable String username, @AuthenticationPrincipal CustomUserDetails userDetails) {
    followService.toggleFollow(userDetails.getId(),username);
        return "redirect:/users/" + username;
    }
}
