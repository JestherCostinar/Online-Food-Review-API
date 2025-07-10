package com.kasmir.ofr.service;

import com.kasmir.ofr.dto.request.UserRequestDto;
import com.kasmir.ofr.dto.response.UserResponseDto;
import com.kasmir.ofr.entity.User;
import com.kasmir.ofr.exception.EmailAlreadyExistException;
import com.kasmir.ofr.exception.ResourceNotFoundException;
import com.kasmir.ofr.mapper.UserMapper;
import com.kasmir.ofr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Create user with email: {}", userRequestDto.getEmail());

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            log.warn("Email already exists: {}", userRequestDto.getEmail());
            throw new EmailAlreadyExistException("User Email Already exist in the record");
        }

        User user = UserMapper.convertToUser(userRequestDto);
        User savedUser = userRepository.save(user);

        return UserMapper.convertToUserResponse(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Retrieving all user records");

        List<User> users = userRepository.findAll();

        return users.stream()
                .map((UserMapper::convertToUserResponse))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        log.info("Retrieving user by ID: {}", id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            log.warn("User not found with ID: {}", id);
            throw new ResourceNotFoundException("User", "id", id);
        }

        User user = optionalUser.get();
        return UserMapper.convertToUserResponse(user);
    }
    


}
