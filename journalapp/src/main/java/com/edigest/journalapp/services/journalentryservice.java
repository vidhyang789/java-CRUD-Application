package com.edigest.journalapp.services;

import com.edigest.journalapp.entity.JournalEntry;
import com.edigest.journalapp.entity.User;
import com.edigest.journalapp.repositeries.journalentryrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalentryservice {

    @Autowired
    private journalentryrepo JournalEntryRepositery;

    @Autowired
    private Userservice userservice;

//    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userservice.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = JournalEntryRepositery.save(journalEntry);
            user.getJournalEntries().add(saved);
            userservice.saveuser(user);
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry..",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        JournalEntryRepositery.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return JournalEntryRepositery.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return JournalEntryRepositery.findById(String.valueOf(id));
    }

    public boolean deleteByid(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userservice.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userservice.saveuser(user);
                JournalEntryRepositery.deleteById(String.valueOf(id));
            }

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting",e);
        }
        return removed;
    }
}
