package com.feedback_service.feedback_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/feedback")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/addUser")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String login, @RequestParam String pass) {
        User newUser = new User();
        newUser.setUser_name(name);
        newUser.setLogin(login);
        newUser.setPass(pass);
        userRepository.save(newUser);
        return "Saved";
    }

    @GetMapping(path = "/allUser")
    public @ResponseBody Iterable<User> getAllUser() {
        return userRepository.findAll();
    }
}
