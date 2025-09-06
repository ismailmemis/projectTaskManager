package com.taskmanager.infrastructure.bootstrap;

import com.taskmanager.infrastructure.persistance.ProjectEntity;
import com.taskmanager.infrastructure.persistance.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initProjects(ProjectRepository projectRepository) {
        return args -> {
            OffsetDateTime now = OffsetDateTime.now();
            List<ProjectEntity> seed = List.of(
                    new ProjectEntity(null, "Projekt 1", "Erstes Projekt", now.minusDays(5), now),
                    new ProjectEntity(null, "Projekt 2", "Zweites Projekt", now.minusDays(4), now),
                    new ProjectEntity(null, "Projekt 3", "Drittes Projekt", now.minusDays(3), now),
                    new ProjectEntity(null, "Projekt 4", "Viertes Projekt", now.minusDays(2), now),
                    new ProjectEntity(null, "Projekt 5", "Fünftes Projekt", now.minusDays(1), now)
            );
            var saved = projectRepository.saveAll(seed);
        };
    }
}
