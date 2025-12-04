package com.ll.instagram.profile;

import com.ll.instagram.common.security.CustomUserDetails;
import com.ll.instagram.user.UserResponse;
import com.ll.instagram.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/edit")
    public String editFrom(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponse currentUser = userService.getUserById(userDetails.getId());
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setBio(currentUser.getBio());
        profileUpdateRequest.setName(currentUser.getName());

        model.addAttribute("profileUpdateRequest", profileUpdateRequest);
        model.addAttribute("currentUser", currentUser);
        return "profile/edit";
    }
}
