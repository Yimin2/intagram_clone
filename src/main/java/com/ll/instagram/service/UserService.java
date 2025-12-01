package com.ll.instagram.service;

import com.ll.instagram.dto.request.SignUpRequest;
import com.ll.instagram.entity.User;

public interface UserService {
    User register(SignUpRequest signUpRequest);
}
