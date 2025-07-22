package com.example.Task.service;

import com.example.Task.dto.TaskRequest;
import com.example.Task.entity.TaskEntity;
import com.example.Task.entity.common.TaskStatus;
import com.example.Task.repository.TaskRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Mono<TaskEntity> save(TaskRequest request) {
        TaskEntity task = new TaskEntity();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.valueOf(request.getStatus().toUpperCase()));

        return taskRepository.save(task);
    }

    public Mono<TaskEntity> updateStatus(Long id,String statusString) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new ChangeSetPersister.NotFoundException()))
                .flatMap(task -> {
                    task.setStatus(TaskStatus.valueOf(statusString.toUpperCase()));
                    task.setUpdatedAt(LocalDateTime.now());
                    return taskRepository.save(task);
                        });
    }

    public Mono<Void> deleteTask(Long id) {
        return taskRepository.deleteById(id);
    }
    public Flux<TaskEntity> findTaskAll() {
        return taskRepository.findAll();
    }
    public Mono<TaskEntity> findTaskById(Long id) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Task not found with id:  + id")));
    }
}
