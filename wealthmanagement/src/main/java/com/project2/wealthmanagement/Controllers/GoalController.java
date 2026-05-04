package com.project2.wealthmanagement.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Models.Goal;
import com.project2.wealthmanagement.Services.ClientRecordsService;
import com.project2.wealthmanagement.Services.GoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/goal")
public class GoalController {




    private final GoalService service;

    public GoalController(GoalService service){
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(service.getAllGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable String id) {
        return ResponseEntity.ok(service.getGoalById(id));
    }




    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        return ResponseEntity.ok(service.createGoal(goal));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable String id, @Valid @RequestBody Goal newGoal) {
        Goal existingGoal = service.getGoalById(id);
        if (existingGoal == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(service.updateGoal(id, newGoal), HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable String id) {
        service.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }










}
