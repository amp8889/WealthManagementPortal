package com.project2.wealthmanagement.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project2.wealthmanagement.Models.ClientRecords;

public interface ClientRecordsRepository extends MongoRepository<ClientRecords, String>{
    

}
