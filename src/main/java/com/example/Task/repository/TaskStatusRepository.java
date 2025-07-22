package com.example.Task.repository;

import com.example.Task.entity.TaskStatusEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TaskStatusRepository extends R2dbcRepository<TaskStatusEntity, Long> {
    Flux<TaskStatusEntity> getAllByTaskId(Long taskId);
}
