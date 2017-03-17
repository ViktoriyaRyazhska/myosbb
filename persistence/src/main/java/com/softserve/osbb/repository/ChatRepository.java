package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.softserve.osbb.model.Chat;



@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
	
	@Modifying
	@Query(value="DELETE FROM chat ORDER BY chat_id ASC limit 10", nativeQuery = true)
	 public void deleteHalf();
	
	@Modifying
	@Query(value="SELECT * FROM chat ORDER BY chat_id ASC limit 10", nativeQuery = true)
	public List<Chat> getHalf();
}
