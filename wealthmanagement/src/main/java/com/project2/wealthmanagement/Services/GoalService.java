package com.project2.wealthmanagement.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project2.wealthmanagement.Models.Goal;
import com.project2.wealthmanagement.Repositories.GoalRepository;

@Service
public class GoalService {

    private final GoalRepository repository;

    public GoalService(GoalRepository repository) {
        this.repository = repository;
    }

    public List<Goal> getAllGoals() {
        return repository.findAll();
    }

    public Goal getGoalById(String id) {
        Optional<Goal> goal = repository.findById(id);
        if (goal.isPresent())
            return goal.get();
        return null;
    }

    public Goal createGoal(Goal goal) {
        return repository.save(goal);
    }

    public Goal updateGoal(String id, Goal goal) {
        goal.setGoalId(id);
        return repository.save(goal);
    }

    public boolean deleteGoal(String id) {
        if (getGoalById(id) == null) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
