package com.feedback_service.feedback_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(path = "/feedback")
public class RegistrationController {
    @Autowired
    private RegistrationRepository registrationRepository;

    @PostMapping(path = "/addRegistration")
    public @ResponseBody String addNewRegistration (@RequestParam String fio,
                                                    @RequestParam String phone,
                                                    @RequestParam String doctor,
                                                    @RequestParam Date date,
                                                    @RequestParam boolean isRegistered,
                                                    @RequestParam String comments) {
        Registration newRegistration = new Registration();
        newRegistration.setFio(fio);
        newRegistration.setPhone(phone);
        newRegistration.setDoctor(doctor);
        newRegistration.setDateRegistration(date);
        newRegistration.setRegistered(isRegistered);
        newRegistration.setComments(comments);
        return "Create";
    }

    @GetMapping(path="/allRegistration")
    public @ResponseBody Iterable<Registration> getAllRegistration() {
        return registrationRepository.findAll();
    }
}
