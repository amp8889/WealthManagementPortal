package com.project2.wealthmanagement.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Models.Goal;

@Repository
public interface GoalRepository extends MongoRepository<Goal, String> {

    // List<Goal> getClientRecordById(String goalId);

}
