package com.feedback_service.feedback_service.controller.v1;

import com.feedback_service.feedback_service.dto.registration.CreateRegistrationDto;
import com.feedback_service.feedback_service.dto.registration.RegistrationDto;
import com.feedback_service.feedback_service.dto.registration.UpdateRegistrationDto;
import com.feedback_service.feedback_service.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public List<RegistrationDto> findAllByOrderById(@RequestParam Integer page, @RequestParam Integer size) {
        return registrationService.findAllRegistrationDtoByOrderById(page, size);
    }

    @GetMapping(path = "/registration/{id}")
    public ResponseEntity<RegistrationDto> getAllRegistration(@PathVariable Integer id) {
        return ResponseEntity.ok(registrationService.findRegistrationDtoById(id));
    }

    @PutMapping(path="/registration/{id}")
    public ResponseEntity<RegistrationDto> updateRegistrationById(@PathVariable Integer id,
                                                                     @Valid @RequestBody UpdateRegistrationDto updateRegistrationDto){
        return ResponseEntity.ok(registrationService.updateRegistrationDto(id, updateRegistrationDto));
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<RegistrationDto> createRegistration(
            @RequestBody CreateRegistrationDto newRegistration
    ) throws ResponseStatusException
    {
        try {
            return ResponseEntity.ok(registrationService.createRegistrationDto(newRegistration, newRegistration.getUserId()));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
