package com.clush.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.clush.entity.SharedCalendarEntity;
import com.clush.entity.SharedToDoEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SharedCalendarDto {
    private long id;
    private String name;
    private List<SharedToDoDto> sharedTodos;


    public SharedCalendarDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public SharedCalendarDto(SharedCalendarEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.sharedTodos = entity.getSharedTodos().stream()
                                 .map(SharedToDoDto::new)
                                 .collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    public static class SharedToDoDto {
        private long id;
        private String title;
        private String status;
        private String description;
        private String date;
        private int priority;

        public SharedToDoDto(SharedToDoEntity todo) {
            this.id = todo.getId();
            this.title = todo.getTitle();
            this.status = todo.getStatus();
            this.description = todo.getDescription();
            this.date = todo.getDate();
            this.priority = todo.getPriority();
            
        }
    }
}
