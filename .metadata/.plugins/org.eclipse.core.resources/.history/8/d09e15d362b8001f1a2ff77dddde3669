package com.clush.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clush.entity.CalendarEntity;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{
	CalendarEntity findByDate(String today);
}
