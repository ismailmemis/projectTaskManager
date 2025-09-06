package com.taskmanager.infrastructure.bootstrap;

import com.taskmanager.infrastructure.persistance.ProjectEntity;
import com.taskmanager.infrastructure.persistance.ProjectRepository;
import com.taskmanager.infrastructure.persistance.TaskEntity;
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

            // Projekte anlegen
            var p1 = new ProjectEntity(null, "Projekt 1", "Erstes Projekt", now.minusDays(5), now.minusDays(5));
            var p2 = new ProjectEntity(null, "Projekt 2", "Zweites Projekt", now.minusDays(4), now.minusDays(4));
            var p3 = new ProjectEntity(null, "Projekt 3", "Drittes Projekt", now.minusDays(3), now.minusDays(3));

            // Tasks erzeugen und eindeutig zuordnen (jede Task genau 1 Projekt)
            p1.addTask(new TaskEntity(null, "Analyse", "Scope klären",
                    TaskEntity.TaskStatus.OFFEN, p1, now.minusDays(5), now.minusDays(5)));
            p1.addTask(new TaskEntity(null, "Implementierung", "Feature A",
                    TaskEntity.TaskStatus.IN_BEARBEITUNG, p1, now.minusDays(4), now.minusDays(2)));

            p2.addTask(new TaskEntity(null, "Testing", "Unit & Integration",
                    TaskEntity.TaskStatus.OFFEN, p2, now.minusDays(3), now.minusDays(3)));
            p2.addTask(new TaskEntity(null, "Dokumentation", "Readme/Guides",
                    TaskEntity.TaskStatus.ERLEDIGT, p2, now.minusDays(2), now.minusDays(1)));

            p3.addTask(new TaskEntity(null, "CI-Setup", "Build-Pipeline",
                    TaskEntity.TaskStatus.OFFEN, p3, now.minusDays(1), now.minusDays(1)));

            // Persistieren – Cascade.ALL in ProjectEntity sorgt fürs Mitspeichern der Tasks
            projectRepository.saveAll(List.of(p1, p2, p3));
        };
    }
}
