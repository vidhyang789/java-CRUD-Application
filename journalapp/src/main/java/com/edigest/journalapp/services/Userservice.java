package com.edigest.journalapp.services;

import com.edigest.journalapp.entity.User;
import com.edigest.journalapp.repositeries.Userrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Userservice {

    @Autowired
    private Userrepo UserRepositery;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void SaveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            UserRepositery.save(user);
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public void saveuser(User user){
        try{
            UserRepositery.save(user);
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public List<User> getAll(){
        return UserRepositery.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return UserRepositery.findById(String.valueOf(id));
    }

    public void deleteByid(ObjectId id){
        UserRepositery.deleteById(String.valueOf(id));
    }

    public User findByUserName(String username){
        return UserRepositery.findByUserName(username);
    }

    public void saveAdmin(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            UserRepositery.save(user);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
