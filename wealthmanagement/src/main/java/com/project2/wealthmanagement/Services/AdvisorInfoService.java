package com.project2.wealthmanagement.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project2.wealthmanagement.Models.AdvisorInfo;
import com.project2.wealthmanagement.Repositories.AdvisorInfoRepository;

@Service
public class AdvisorInfoService {

    @Autowired
    private AdvisorInfoRepository advisorInfoRepository;

    // POST/PUT advisor
    public AdvisorInfo saveAdvisor(AdvisorInfo advisor) {
        return advisorInfoRepository.save(advisor);
    }

    // GET advisor by advisorId
    public AdvisorInfo getAdvisorById(String advisorId) {
        Optional<AdvisorInfo> optional = advisorInfoRepository.findById(advisorId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            // TODO: Custom runtime exception message
            throw new RuntimeException();
        }
    }

    // GET advisor by userId
    public AdvisorInfo getAdvisorByUserId(String userId) {
        Optional<AdvisorInfo> optional = advisorInfoRepository.findByUserId(userId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            // TODO: Custom runtime exception message
            throw new RuntimeException();
        }
    }

    // GET all advisors
    public List<AdvisorInfo> getAllAdvisors() {
        return advisorInfoRepository.findAll();
    }

    // GET advisors by specialization
    public List<AdvisorInfo> getAdvisorsBySpecialization(String specialization) {
        return advisorInfoRepository.findBySpecialization(specialization);
    }

    // DELETE advisor
    public void deleteAdvisor(String advisorId) {
        advisorInfoRepository.deleteById(advisorId);
    }

    // PUT client to advisor
    public void assignClientToAdvisor(String advisorId, String clientId) {
        AdvisorInfo advisor = getAdvisorById(advisorId);
        List<String> clients = advisor.getAssignedClientIds();
        if (!clients.contains(clientId)) {
            clients.add(clientId);
            advisor.setAssignedClientIds(clients);
            advisorInfoRepository.save(advisor);
        }
    }

    // PUT remove client
    public void removeClientFromAdvisor(String advisorId, String clientId) {
        AdvisorInfo advisor = getAdvisorById(advisorId);
        List<String> clients = advisor.getAssignedClientIds();
        clients.remove(clientId);
        advisor.setAssignedClientIds(clients);
        advisorInfoRepository.save(advisor);
    }
}