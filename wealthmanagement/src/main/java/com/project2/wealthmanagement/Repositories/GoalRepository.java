package com.project2.wealthmanagement.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Models.Goal;

public interface GoalRepository extends MongoRepository<Goal, String> {

    List<Goal> getClientRecordById(String id);

}
