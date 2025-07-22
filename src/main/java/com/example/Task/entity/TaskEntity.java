package com.example.Task.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("tasks")
@Data
public class TaskEntity {
    @Id
    private long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
