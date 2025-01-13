package com.edigest.journalapp.controller;

import com.edigest.journalapp.entity.User;
import com.edigest.journalapp.services.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    Userservice userservice;

    @GetMapping
    public ResponseEntity<?> getallusers(){
        List<User> all = userservice.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createnewadmin(@RequestBody User user){
        userservice.saveAdmin(user);
    }

}
