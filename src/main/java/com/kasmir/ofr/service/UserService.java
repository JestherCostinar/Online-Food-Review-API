package com.kasmir.ofr.service;

import com.kasmir.ofr.dto.request.UserRequestDto;
import com.kasmir.ofr.dto.response.UserResponseDto;
import com.kasmir.ofr.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    UserResponseDto createUser(UserRequestDto user);
}
