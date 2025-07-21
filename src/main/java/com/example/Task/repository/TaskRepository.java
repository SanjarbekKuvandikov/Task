package com.example.Task.repository;

import com.example.Task.entity.TaskEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<TaskEntity,Long> {
}
