package com.ll.instagram.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "아이디 입력")
    @Size(min = 3, max = 20, message = "아이디 3~20자만")
    private String username;

    @Size(min = 4, max = 20, message = "비밀번호 4~20자만")
    @NotBlank(message = "비밀번호 입력")
    private String password;

    @Email
    @NotBlank(message = "이메일 입력")
    private String email;

    @NotBlank(message = "이름 입력")
    private String name;
}
