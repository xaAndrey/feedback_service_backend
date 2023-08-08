package com.feedback_service.feedback_service.service.user;

import com.feedback_service.feedback_service.dto.user.CreateUserDto;
import com.feedback_service.feedback_service.dto.user.UserDto;
import com.feedback_service.feedback_service.entity.user.UserEntity;
import com.feedback_service.feedback_service.exception.UserAlreadyExistException;
import com.feedback_service.feedback_service.exception.UserNotFoundException;
import com.feedback_service.feedback_service.repository.user.UserRepository;
import com.feedback_service.feedback_service.util.error.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> findUserDtoAll() {
        List<UserEntity> newUserEntity = userRepository.findAll();
        ArrayList<UserDto> newUserDto = new ArrayList<>();
        for (UserEntity userEntity : newUserEntity) {
            newUserDto.add(convertToDto(userEntity));
        }
        return newUserDto;
    }

    public UserEntity findUserEntityById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " not found.", HttpStatus.NOT_FOUND, ErrorCode.UNF));
    }

    public UserDto findUserDtoById(Integer userId) throws UserNotFoundException {
        return convertToDto(findUserEntityById(userId));
    }

    public UserEntity findUserEntityByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found.", HttpStatus.NOT_FOUND, ErrorCode.UNF));
    }

    public UserDto findUserDtoByUsername(String username) throws UserNotFoundException {
        return convertToDto(findUserEntityByUsername(username));
    }

    public UserEntity createUserEntity(CreateUserDto newUser) throws UserAlreadyExistException {
        UserEntity user = modelMapper.map(newUser, UserEntity.class);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setSecurityStamp(generateSecurityStamp());
        user.setRegistrationDate(ZonedDateTime.now());
        user.setAccountLocked(false);

        try {
           return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
           throw new UserAlreadyExistException("User with username: " + user.getUsername() + " already exists.");
        }
    }

    public UserDto createUserDto(CreateUserDto newUser) throws UserAlreadyExistException {
        return convertToDto(createUserEntity(newUser));
    }

    private UserDto convertToDto(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

    private UserDto convertToEntity(UserDto user) {
        return modelMapper.map(user, UserDto.class);
    }

    public boolean anyUserExists() {
       return userRepository.existsByIdIsGreaterThan(0);
    }

    private String generateSecurityStamp() {
        String securityStamp;
        do {
            securityStamp = UUID.randomUUID().toString().replace("-", "");
        } while (userRepository.getBySecurityStamp(securityStamp).isPresent());
        return securityStamp;
    }
}




