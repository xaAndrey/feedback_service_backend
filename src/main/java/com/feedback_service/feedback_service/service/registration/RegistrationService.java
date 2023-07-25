package com.feedback_service.feedback_service.service.registration;

import com.feedback_service.feedback_service.dto.registration.CreateRegistrationDto;
import com.feedback_service.feedback_service.dto.registration.RegistrationDto;
import com.feedback_service.feedback_service.entity.registration.RegistrationEntity;
import com.feedback_service.feedback_service.dto.user.UserDto;
import com.feedback_service.feedback_service.exception.RegistrationNotFound;
import com.feedback_service.feedback_service.repository.registration.RegistrationRepository;
import com.feedback_service.feedback_service.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public RegistrationEntity findRegistrationEntityById(Integer registrationId) throws RegistrationNotFound {
        return registrationRepository.findById(registrationId).orElseThrow(() -> new RegistrationNotFound("Registration with ID: " + registrationId + " not found."));
    }

    public RegistrationDto findRegistrationDtoById(Integer registrationId) throws RegistrationNotFound {
        return convertToDto(findRegistrationEntityById(registrationId));
    }

    public RegistrationEntity createRegistrationEntity(
            CreateRegistrationDto newRegistration,
            Integer userId
    ) throws DataIntegrityViolationException
    {
        RegistrationEntity registration = modelMapper.map(newRegistration, RegistrationEntity.class);
        registration.setFio(registration.getFio());
        registration.setPhone(registration.getPhone());
        registration.setDoctor(registration.getDoctor());
        registration.setDateRegistration(LocalDate.now());
        registration.setRegistered(false);
        registration.setComments(registration.getComments());

        UserDto user = modelMapper.map(userRepository.findById(userId).orElseThrow(), UserDto.class);
        registration.setUser(user);

        try {
            return registrationRepository.save(registration);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Registration don't create");
        }
    }

    public RegistrationDto createRegistrationDto(
            CreateRegistrationDto newRegistration,
            Integer userId
    ) throws DataIntegrityViolationException
    {
        return convertToDto(createRegistrationEntity(newRegistration, userId));
    }

    public RegistrationDto convertToDto(RegistrationEntity registration) {
        return modelMapper.map(registration, RegistrationDto.class);
    }

    public RegistrationEntity convertToEntity(RegistrationDto registration) {
        return modelMapper.map(registration, RegistrationEntity.class);
    }
}
