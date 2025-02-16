package com.clush.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clush.entity.CalendarEntity;


public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{
	CalendarEntity findByIdAndDate(Long id, String today);
	@Query("SELECT c FROM CalendarEntity c WHERE c.user.id = :userId AND c.date LIKE :yearMonth%")
	List<CalendarEntity> findByUserIdAndDateStartingWith(@Param("userId") Long userId, @Param("yearMonth") String yearMonth);

}
