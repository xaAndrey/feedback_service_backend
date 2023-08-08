package com.feedback_service.feedback_service.service.registration;

import com.feedback_service.feedback_service.dto.PagedResultsDto;
import com.feedback_service.feedback_service.dto.registration.CreateRegistrationDto;
import com.feedback_service.feedback_service.dto.registration.RegistrationDto;
import com.feedback_service.feedback_service.dto.registration.UpdateRegistrationDto;
import com.feedback_service.feedback_service.entity.registration.RegistrationEntity;
import com.feedback_service.feedback_service.entity.user.UserEntity;
import com.feedback_service.feedback_service.exception.RegistrationNotFoundException;
import com.feedback_service.feedback_service.repository.registration.RegistrationRepository;
import com.feedback_service.feedback_service.repository.user.UserRepository;
import com.feedback_service.feedback_service.util.error.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(
            RegistrationRepository registrationRepository,
            ModelMapper modelMapper,
            UserRepository userRepository
    ) {
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

    }

    public List<RegistrationDto> findRegistrationDtoAll() {
        List<RegistrationEntity> newRegistrationEntity = registrationRepository.findAll();
        ArrayList<RegistrationDto> newRegistrationDto = new ArrayList<>();
        for (RegistrationEntity registrationEntity : newRegistrationEntity) {
            newRegistrationDto.add(convertToDto(registrationEntity));
        }
        return newRegistrationDto;
    }

    public RegistrationEntity findRegistrationEntityById(Integer registrationId) throws RegistrationNotFoundException {
        return registrationRepository.findById(registrationId).orElseThrow(() -> new RegistrationNotFoundException("Registration with ID: " + registrationId + " not found.", HttpStatus.NOT_FOUND, ErrorCode.RNF));
    }

    public RegistrationDto findRegistrationDtoById(Integer registrationId) throws RegistrationNotFoundException {
        return convertToDto(findRegistrationEntityById(registrationId));
    }

    public List<RegistrationEntity> findAllRegistrationEntityByOrderById(Integer page, Integer size) {
        return registrationRepository.findAllByOrderById(PageRequest.of(page, size));
    }

    public PagedResultsDto<RegistrationDto> findAllRegistrationDtoByOrderById(Integer page, Integer size) {
        List<RegistrationEntity> newRegistrationEntity = findAllRegistrationEntityByOrderById(page, size);
        ArrayList<RegistrationDto> newRegistrationDto = new ArrayList<>();
        for (RegistrationEntity registrationEntity : newRegistrationEntity) {
            newRegistrationDto.add(convertToDto(registrationEntity));
        }
        Integer count = newRegistrationDto.size();
        return new PagedResultsDto<>(newRegistrationDto, count);
    }

    public RegistrationEntity createRegistrationEntity(
            CreateRegistrationDto newRegistration
    ) throws DataIntegrityViolationException {
        RegistrationEntity registration = modelMapper.map(newRegistration, RegistrationEntity.class);
        registration.setId(null);
        registration.setDateRegistration(ZonedDateTime.now());
        registration.setRegistered(false);

        newRegistration.setUserId(1);

        UserEntity user = userRepository.findById(newRegistration.getUserId()).orElseThrow();
        registration.setUser(user);

        try {
            return registrationRepository.save(registration);
        } catch (Exception e) {
            System.err.println("Don't create registrations: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public RegistrationDto createRegistrationDto(
            CreateRegistrationDto newRegistration
    ) throws DataIntegrityViolationException {
        return convertToDto(createRegistrationEntity(newRegistration));
    }

    public RegistrationEntity updateRegistrationEntity(
            Integer id,
            UpdateRegistrationDto updateRegistrationDto
    ) {
        RegistrationEntity updateRegistrationEntity = findRegistrationEntityById(id);
        updateRegistrationEntity.setRegistered(updateRegistrationDto.getIsRegistered());
        return registrationRepository.save(updateRegistrationEntity);
    }

    public RegistrationDto updateRegistrationDto(
            Integer id,
            UpdateRegistrationDto updateRegistrationDto
    ) {
        return convertToDto(updateRegistrationEntity(id, updateRegistrationDto));
    }

    public RegistrationDto convertToDto(RegistrationEntity registration) {
        return modelMapper.map(registration, RegistrationDto.class);
    }

    public RegistrationEntity convertToEntity(RegistrationDto registration) {
        return modelMapper.map(registration, RegistrationEntity.class);
    }
}
