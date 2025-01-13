package com.edigest.journalapp.controller;

import com.edigest.journalapp.entity.User;
import com.edigest.journalapp.services.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private Userservice userservice;

    @GetMapping("/health-check")
    public String healthcheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userservice.saveEntry(user);
    }
}
