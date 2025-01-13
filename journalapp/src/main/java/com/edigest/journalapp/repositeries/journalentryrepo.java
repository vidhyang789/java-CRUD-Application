package com.edigest.journalapp.repositeries;

import com.edigest.journalapp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalentryrepo extends MongoRepository<JournalEntry,String> {
}
