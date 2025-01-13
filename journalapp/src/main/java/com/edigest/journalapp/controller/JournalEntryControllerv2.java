package com.edigest.journalapp.controller;

import com.edigest.journalapp.entity.JournalEntry;
import com.edigest.journalapp.entity.User;
import com.edigest.journalapp.services.Userservice;
import com.edigest.journalapp.services.journalentryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private journalentryservice JournalEntryService;


    @Autowired
    private Userservice userservice;

    @GetMapping
    public ResponseEntity<?> getAllJournalkEntryOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userservice.findByUserName(userName);
        List<JournalEntry> all =  user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJouralEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userservice.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = JournalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = JournalEntryService.deleteByid(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> UpdateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userservice.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = JournalEntryService.findById(id);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
                old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
                JournalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        JournalEntry journalEntry = JournalEntryService.findById(id).orElse(null);
        if(journalEntry != null) {
            journalEntry.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : journalEntry.getContent());
            JournalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
