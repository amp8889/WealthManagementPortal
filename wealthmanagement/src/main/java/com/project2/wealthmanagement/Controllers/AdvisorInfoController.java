package com.project2.wealthmanagement.Controllers;

import java.util.List;

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

// TODO: Fill out controller functions
@RestController
@RequestMapping("/api/advisors")
public class AdvisorInfoController {
    
    // POST new advisor
    @PostMapping
    public ResponseEntity<AdvisorInfo> createAdvisor(@RequestBody AdvisorInfo advisor) {
    }
    
    // PUT advisor
    @PutMapping
    public ResponseEntity<AdvisorInfo> updateAdvisor(@RequestBody AdvisorInfo advisor) {
    }

    // GET advisor by advisorId
    @GetMapping("/{advisorId}") 
    public ResponseEntity<AdvisorInfo> getAdvisorById(@RequestBody String advisorId) {
    }
    
    // GET advisor by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<AdvisorInfo> getAdvisorByUserId(@PathVariable String userId) {
    }
    
    // GET all advisors
    @GetMapping
    public ResponseEntity<List<AdvisorInfo>> getAllAdvisors() {
    }
    
    // GET advisors by specialization
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<AdvisorInfo>> getAdvisorsBySpecialization(@PathVariable String specialization) {
    }
    
    // DELETE advisor by advisorId
    @DeleteMapping("/{advisorId}")
    public ResponseEntity<Void> deleteAdvisor(@PathVariable String advisorId) {
    }
    
    // POST add client to advisor
    @PostMapping("/{advisorId}/clients/{clientId}")
    public ResponseEntity<Void> assignClientToAdvisor(@PathVariable String advisorId, @PathVariable String clientId) {
    }
    
    // DELETE client from advisor
    @DeleteMapping("/{advisorId}/clients/{clientId}")
    public ResponseEntity<Void> removeClientFromAdvisor(@PathVariable String advisorId, @PathVariable String clientId) {
    }
}