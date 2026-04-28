package com.project2.wealthmanagement.Configurations;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project2.wealthmanagement.Enums.ClientTier;
import com.project2.wealthmanagement.Enums.PrimaryObjective;
import com.project2.wealthmanagement.Enums.RiskTolerance;
import com.project2.wealthmanagement.Models.ClientRecords;
import com.project2.wealthmanagement.Repositories.ClientRecordsRepository;

@Configuration
public class DataLoader {


    @Bean
    CommandLineRunner init(ClientRecordsRepository repository){
        return args -> {
            repository.save(new ClientRecords("1", "Aidan", "Pavlik", ClientTier.STANDARD, "USA", RiskTolerance.AGGRESSIVE, PrimaryObjective.BALANCED, List.of("1", "2")));
        };
    }
}
