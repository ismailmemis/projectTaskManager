package com.taskmanager.infrastructure.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByProjectIsNull();

    List<TaskEntity> findByProject_Id(Long projectId);
}
