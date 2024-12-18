package com.clush.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clush.entity.CalendarEntity;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{
	CalendarEntity findByDate(String today);
	List<CalendarEntity> findByDateStartingWith(String yearMonth);
}
