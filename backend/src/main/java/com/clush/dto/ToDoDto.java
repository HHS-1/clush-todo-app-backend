package com.clush.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoDto {
	private String title;
	private String description;
	private String date;
	private int priority;
	private String status;
}
