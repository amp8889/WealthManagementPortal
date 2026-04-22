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
import org.springframework.web.bind.annotation.RestController;

import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Services.ClientRecordsService;

import jakarta.validation.Valid;

@RestController
public class ClientRecordsController {

    private final ClientRecordsService service;

    public ClientRecordsController(ClientRecordsService service){
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<ClientRecords>> getAllClientRecords() {
        return ResponseEntity.ok(service.getAllClientRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientRecords> getClientRecordById(@PathVariable String id) {
        return ResponseEntity.ok(service.getClientRecordById(id));
    }




    @PostMapping
    public ResponseEntity<ClientRecords> createClientRecords(@RequestBody ClientRecords clientRecords) {
        return ResponseEntity.ok(service.createClientRecords(clientRecords));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClientRecords> updateClientRecord(@PathVariable String id, @Valid @RequestBody ClientRecords newClientRecord) {
        ClientRecords existingClientRecord = service.getClientRecordById(id);
        if (existingClientRecord == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(service.updateClientRecords(id, newClientRecord), HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientRecord(@PathVariable String id) {
        service.deleteClientRecords(id);
        return ResponseEntity.noContent().build();
    }

}
