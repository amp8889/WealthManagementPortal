package com.project2.wealthmanagement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.project2.wealthmanagement.Models.AdvisorInfo;
import com.project2.wealthmanagement.Services.AdvisorInfoService;

@RestController
@RequestMapping("/api/advisors")
public class AdvisorInfoController {

    @Autowired
    private AdvisorInfoService advisorInfoService;

   // POST new advisor
    @PostMapping
    public ResponseEntity<AdvisorInfo> createAdvisor(@RequestBody AdvisorInfo advisor) {
        AdvisorInfo createdAdvisor = advisorInfoService.saveAdvisor(advisor);
        return new ResponseEntity<>(createdAdvisor, HttpStatus.CREATED);
    }
    
    // PUT advisor
    @PutMapping
    public ResponseEntity<AdvisorInfo> updateAdvisor(@RequestBody AdvisorInfo advisor) {
        AdvisorInfo updatedAdvisor = advisorInfoService.saveAdvisor(advisor);
        return ResponseEntity.ok(updatedAdvisor);
    }

    // GET advisor by advisorId
    @GetMapping("/{advisorId}") 
    public ResponseEntity<AdvisorInfo> getAdvisorById(@PathVariable String advisorId) {
        AdvisorInfo advisor = advisorInfoService.getAdvisorById(advisorId);
        return ResponseEntity.ok(advisor);
    }
    
    // GET advisor by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<AdvisorInfo> getAdvisorByUserId(@PathVariable String userId) {
        AdvisorInfo advisor = advisorInfoService.getAdvisorByUserId(userId);
        return ResponseEntity.ok(advisor);
    }
    
    // GET all advisors
    @GetMapping
    public ResponseEntity<List<AdvisorInfo>> getAllAdvisors() {
        List<AdvisorInfo> advisors = advisorInfoService.getAllAdvisors();
        return ResponseEntity.ok(advisors);
    }
    
    // GET advisors by specialization
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<AdvisorInfo>> getAdvisorsBySpecialization(@PathVariable String specialization) {
        List<AdvisorInfo> advisors = advisorInfoService.getAdvisorsBySpecialization(specialization);
        return ResponseEntity.ok(advisors);
    }
    
    // DELETE advisor by advisorId
    @DeleteMapping("/{advisorId}")
    public ResponseEntity<Void> deleteAdvisor(@PathVariable String advisorId) {
        advisorInfoService.deleteAdvisor(advisorId);
        return ResponseEntity.noContent().build();
    }
    
    // POST add client to advisor
    @PostMapping("/{advisorId}/clients/{clientId}")
    public ResponseEntity<Void> assignClientToAdvisor(@PathVariable String advisorId, @PathVariable String clientId) {
        advisorInfoService.assignClientToAdvisor(advisorId, clientId);
        return ResponseEntity.ok().build();
    }
    
    // DELETE client from advisor
    @DeleteMapping("/{advisorId}/clients/{clientId}")
    public ResponseEntity<Void> removeClientFromAdvisor(@PathVariable String advisorId, @PathVariable String clientId) {
        advisorInfoService.removeClientFromAdvisor(advisorId, clientId);
        return ResponseEntity.noContent().build();
    }
}