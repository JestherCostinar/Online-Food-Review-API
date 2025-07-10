package com.kasmir.ofr.service;

import com.kasmir.ofr.dto.request.UserRequestDto;
import com.kasmir.ofr.dto.response.UserResponseDto;
import com.kasmir.ofr.entity.User;
import com.kasmir.ofr.exception.EmailAlreadyExistException;
import com.kasmir.ofr.mapper.UserMapper;
import com.kasmir.ofr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto user) {
        System.out.println(user.getEmail());
        Optional<User> fetchEmail = userRepository.findByEmail(user.getEmail());
        System.out.println(fetchEmail);

        if (fetchEmail.isPresent()) {
            throw new EmailAlreadyExistException("User Email Already exist in the record");
        }

        User savedUser = UserMapper.convertToUser(user);
        UserResponseDto userResponse = UserMapper.convertToUserResponse(userRepository.save(savedUser));
        return userResponse;
    }
}
