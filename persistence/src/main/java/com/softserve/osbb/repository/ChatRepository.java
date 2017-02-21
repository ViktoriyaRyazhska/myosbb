package com.softserve.osbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softserve.osbb.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

	
}
