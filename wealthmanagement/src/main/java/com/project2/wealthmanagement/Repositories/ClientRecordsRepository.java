package com.project2.wealthmanagement.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project2.wealthmanagement.Models.ClientRecords;

@Repository
public interface ClientRecordsRepository extends MongoRepository<ClientRecords, String>{
    

}
