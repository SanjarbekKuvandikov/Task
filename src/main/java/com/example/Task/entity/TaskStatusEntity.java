package com.example.Task.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("task_statuses")
@Data
public class TaskStatusEntity {
    @Id
    private Long id;
    private Long taskId;
    private String status;
    private LocalDateTime updated;
}
