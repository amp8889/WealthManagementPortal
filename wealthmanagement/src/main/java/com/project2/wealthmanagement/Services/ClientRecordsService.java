package com.project2.wealthmanagement.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Models.Goal;
import com.project2.wealthmanagement.Repositories.ClientRecordsRepository;
import com.project2.wealthmanagement.Repositories.GoalRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientRecordsService {


    private final ClientRecordsRepository repository;
    private final GoalRepository goalRepository;


    public ClientRecordsService(ClientRecordsRepository repository, GoalRepository goalRepository){
        this.repository = repository;
        this.goalRepository = goalRepository;
    }

    public List<ClientRecords> getAllClientRecords(){
        return repository.findAll();
    }

    public ClientRecords getClientRecordById(String id){
        Optional<ClientRecords> clientRecords = repository.findById(id);
        if (clientRecords.isPresent())
            return clientRecords.get();
        return null;
    }

    // public List<Goal> getGoalsForClient(String id) {
    //     return goalRepository.getClientRecordById(id);
    // }


    public ClientRecords createClientRecords(ClientRecords clientRecords){
        if (clientRecords.getId() == null || clientRecords.getId().isEmpty()) {
            clientRecords.setId(UUID.randomUUID().toString());
        }
        return repository.save(clientRecords);
    }


    public ClientRecords updateClientRecords(String id, ClientRecords clientRecords){
        clientRecords.setId(id);
        return repository.save(clientRecords);
    }





    
    public boolean deleteClientRecords(String id) {
        if(getClientRecordById(id) == null){
            return false;
        }
        repository.deleteById(id);
        return true;
    }




}
