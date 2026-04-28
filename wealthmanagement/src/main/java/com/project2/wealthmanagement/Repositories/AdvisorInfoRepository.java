package com.project2.wealthmanagement.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.project2.wealthmanagement.Models.AdvisorInfo;

@Repository
public interface AdvisorInfoRepository extends MongoRepository<AdvisorInfo, String> {
    
    // Advisor by userId (references user table)
    Optional<AdvisorInfo> findByUserId(String userId);
    
    // Advisor by specialization
    List<AdvisorInfo> findBySpecialization(String specialization);
    
    // Advisor by ClientIds
    List<AdvisorInfo> findByAssignedClientIdsContaining(String clientId);
}