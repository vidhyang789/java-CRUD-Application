package com.edigest.journalapp.services;

import com.edigest.journalapp.entity.User;
import com.edigest.journalapp.repositeries.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class userdetailserviceImpl implements UserDetailsService {

    @Autowired
    private Userrepo userrepositery;

    public userdetailserviceImpl(Userrepo userrepositery) {
        this.userrepositery = userrepositery;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userrepositery.findByUserName(username);
        if(user != null){
            UserDetails userdetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userdetails;
        }
        throw new UsernameNotFoundException("user not found with username : " + username);
    }
}
