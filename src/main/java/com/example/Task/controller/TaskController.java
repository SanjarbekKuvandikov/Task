package com.example.Task.controller;

import com.example.Task.dto.TaskRequest;
import com.example.Task.entity.TaskEntity;
import com.example.Task.entity.TaskStatusEntity;
import com.example.Task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("create")
    public Mono<ResponseEntity<TaskEntity>> createTask(@Valid @RequestBody TaskRequest request) {
        return taskService.save(request).map(ResponseEntity::ok);
    }

    @DeleteMapping("delete")
    public Mono<Void> deleteTask(@RequestParam("id") Long id) {
        return taskService.deleteTask(id);
    }

    @PostMapping("update-status")
    public Mono<ResponseEntity<TaskStatusEntity>> updateStatus(@RequestParam("id") Long id,
                                                               @RequestParam("status") String status){
        return taskService.updateStatus(id, status).map(ResponseEntity::ok);
    }

    @GetMapping("get-all-tasks")
    public Flux<TaskEntity> getAllTasks(){
        return taskService.findTaskAll();
    }

    @GetMapping("get-tasks-by-id")
    public Mono<TaskEntity> getTaskById(@RequestParam("id") Long id){
        return taskService.findTaskById(id);
    }

    @GetMapping("get-all-status")
    public Flux<TaskStatusEntity> getAllStatus(){
        return taskService.findAllStatus();
    }

    @GetMapping("get-status-by-task-id")
    public Flux<TaskStatusEntity> getStatusById(@RequestParam("id") Long id){
        return taskService.findStatusByTaskId(id);
    }

}
