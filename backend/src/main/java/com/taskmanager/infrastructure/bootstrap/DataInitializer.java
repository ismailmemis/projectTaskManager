package com.taskmanager.infrastructure.bootstrap;

import com.taskmanager.infrastructure.User.UserEntity;
import com.taskmanager.infrastructure.User.UserRepository;
import com.taskmanager.infrastructure.project.ProjectEntity;
import com.taskmanager.infrastructure.project.ProjectRepository;
import com.taskmanager.infrastructure.task.TaskEntity;
import com.taskmanager.infrastructure.task.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initProjects(ProjectRepository projectRepository, TaskRepository taskRepository, UserRepository userRepository) {
        return args -> {
            OffsetDateTime now = OffsetDateTime.now();

            // Projekte anlegen
            var p1 = new ProjectEntity("Projekt 1", "Erstes Projekt", now.minusDays(5), now.minusDays(5));
            var p2 = new ProjectEntity("Projekt 2", "Zweites Projekt", now.minusDays(4), now.minusDays(4));
            var p3 = new ProjectEntity("Projekt 3", "Drittes Projekt", now.minusDays(3), now.minusDays(3));


            var t1 = new TaskEntity("Analyse", "Scope klären",
                    TaskEntity.TaskStatus.OFFEN, p1, now.minusDays(5), now.minusDays(5));
            var t2 = new TaskEntity("Implementierung", "Feature A",
                    TaskEntity.TaskStatus.IN_BEARBEITUNG, p1, now.minusDays(4), now.minusDays(2));
            var t3 = new TaskEntity("Testing", "Unit & Integration",
                    TaskEntity.TaskStatus.OFFEN, p2, now.minusDays(3), now.minusDays(3));
            var t4 = new TaskEntity("Dokumentation", "Readme/Guides",
                    TaskEntity.TaskStatus.ERLEDIGT, p2, now.minusDays(2), now.minusDays(1));
            var t5 = new TaskEntity("CI-Setup", "Build-Pipeline",
                    TaskEntity.TaskStatus.OFFEN, p3, now.minusDays(1), now.minusDays(1));

            // Aufgaben ohne Projekt
            var t6 = new TaskEntity("Research", "Neue Technologien evaluieren",
                    TaskEntity.TaskStatus.OFFEN, null, now.minusDays(7), now.minusDays(6));
            var t7 = new TaskEntity("Refactoring", "Codequalität verbessern",
                    TaskEntity.TaskStatus.IN_BEARBEITUNG, null, now.minusDays(2), now.minusDays(1));
            var t8 = new TaskEntity("Brainstorming", "Ideen für nächstes Release sammeln",
                    TaskEntity.TaskStatus.ERLEDIGT, null, now.minusDays(10), now.minusDays(8));


            p1.addTask(t1);
            p1.addTask(t2);

            p2.addTask(t3);
            p2.addTask(t4);

            p3.addTask(t5);
            projectRepository.saveAll(List.of(p1, p2, p3));

            taskRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6, t7, t8));

            // Users anlegen
            var u1 = new UserEntity();
            u1.setName("Alice");

            var u2 = new UserEntity();
            u2.setName("Bob");

            var u3 = new UserEntity();
            u3.setName("Charlie");

            var u4 = new UserEntity();
            u4.setName("Diana");

            userRepository.saveAll(List.of(u1, u2, u3, u4));

            // Tasks zu Users zuweisen
            t1.getUsers().add(u1);
            t1.getUsers().add(u2);

            t2.getUsers().add(u2);

            t3.getUsers().add(u3);

            t6.getUsers().add(u1);
            t6.getUsers().add(u4);

            t7.getUsers().add(u4);

            taskRepository.saveAll(List.of(t1, t2, t3, t6, t7));


        };
    }
}
