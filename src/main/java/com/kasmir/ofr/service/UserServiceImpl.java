package com.kasmir.ofr.service;

import com.kasmir.ofr.dto.request.UserRequestDto;
import com.kasmir.ofr.dto.response.UserResponseDto;
import com.kasmir.ofr.entity.User;
import com.kasmir.ofr.mapper.UserMapper;
import com.kasmir.ofr.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto user) {
        User savedUser = UserMapper.convertToUser(user);
        UserResponseDto userResponse = UserMapper.convertToUserResponse(userRepository.save(savedUser));
        return userResponse;
    }
}
