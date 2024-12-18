package com.clush.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clush.entity.CalendarEntity;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{
	CalendarEntity findByIdAndDate(Long id, String today);
	List<CalendarEntity> findByIdAndDateStartingWith(Long id, String yearMonth);
}
