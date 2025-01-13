package com.edigest.journalapp.repositeries;

import com.edigest.journalapp.entity.JournalEntry;
import com.edigest.journalapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Userrepo extends MongoRepository<User,String> {
    User findByUserName(String username);

    void deleteByUserName(String name);
}
