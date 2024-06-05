package com.snosack.beltexam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.snosack.beltexam.models.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
	List<Player> findAll();
}
