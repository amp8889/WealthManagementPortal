package com.project2.wealthmanagement.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Repositories.ClientRecordsRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientRecordsService {


    private final ClientRecordsRepository repository;


    public ClientRecordsService(ClientRecordsRepository repository){
        this.repository = repository;
    }

    public List<ClientRecords> getAllFunds(){
        return repository.findAll();
    }

    public ClientRecords getClientRecordById(String id){
        Optional<ClientRecords> clientRecords = repository.findById(id);
        if (clientRecords.isPresent())
            return clientRecords.get();
        return null;
    }






    public ClientRecords createClientRecords(ClientRecords clientRecords){
        return repository.save(clientRecords);
    }


    @Transactional
    public ClientRecords updateClientRecords(String id, ClientRecords clientRecords){
        clientRecords.setClientRecordsId(id);
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
