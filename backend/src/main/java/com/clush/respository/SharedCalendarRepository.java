package com.clush.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clush.dto.SharedCalendarDto;
import com.clush.entity.SharedCalendarEntity;

public interface SharedCalendarRepository extends JpaRepository<SharedCalendarEntity, Long>{

	List<SharedCalendarEntity> findByUsersId(long userId);
}
