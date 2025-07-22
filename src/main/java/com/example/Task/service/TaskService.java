package com.example.Task.service;

import com.example.Task.dto.TaskRequest;
import com.example.Task.entity.TaskEntity;
import com.example.Task.entity.TaskStatusEntity;
import com.example.Task.entity.common.TaskStatus;
import com.example.Task.repository.TaskRepository;
import com.example.Task.repository.TaskStatusRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;

    public TaskService(TaskRepository taskRepository, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    public Mono<TaskEntity> save(TaskRequest request) {
        TaskEntity task = new TaskEntity();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task).
                flatMap(savedTask -> {
                    TaskStatusEntity taskStatus = new TaskStatusEntity();
                    taskStatus.setTaskId(task.getId());
                    taskStatus.setStatus(String.valueOf(TaskStatus.TODO));
                    taskStatus.setUpdated(LocalDateTime.now());
                    return taskStatusRepository.save(taskStatus).thenReturn(savedTask);
                });
    }

    public Mono<TaskStatusEntity> updateStatus(Long id,String statusString) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new ChangeSetPersister.NotFoundException()))
                .flatMap(task -> {
                    TaskStatusEntity taskStatus = new TaskStatusEntity();
                    taskStatus.setTaskId(task.getId());
                    taskStatus.setStatus(String.valueOf(TaskStatus.valueOf(statusString.toUpperCase())));
                    taskStatus.setUpdated(LocalDateTime.now());
                    return taskStatusRepository.save(taskStatus);
                        });
    }

    public Mono<Void> deleteTask(Long id) {
        return taskRepository.deleteById(id);
    }
    public Flux<TaskEntity> findTaskAll() {
        return taskRepository.findAll();
    }
    public Mono<TaskEntity> findTaskById(Long id) {
        return taskRepository.findById(id);
    }
    public Flux<TaskStatusEntity> findAllStatus() {
        return taskStatusRepository.findAll();
    }
    public Flux<TaskStatusEntity> findStatusByTaskId(Long taskId) {
        return taskStatusRepository.getAllByTaskId(taskId);
    }
}
