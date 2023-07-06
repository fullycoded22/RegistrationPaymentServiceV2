package org.maccarthyjoshua.api.controller;

import org.maccarthyjoshua.api.model.User;
import org.maccarthyjoshua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user")
    public String setUser(@RequestParam String jsonFilePath){
        return userService.addUser(jsonFilePath);
    }

    //return status code here in this method
    @GetMapping("/user")
    public User getUser(@RequestParam String username){
        Optional<User> user = userService.getUser(username);
        return (User) user.orElse(null);
    }

    @GetMapping("/payments")
    public Optional getPayment(@RequestParam String username){
        Optional user = userService.getUser(username);

        if(user.isPresent()) {
            return userService.getUser(username);
        }
        return Optional.empty();
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
