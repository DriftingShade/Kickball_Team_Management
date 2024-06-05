package com.snosack.beltexam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snosack.beltexam.models.Player;
import com.snosack.beltexam.models.Team;
import com.snosack.beltexam.repositories.PlayerRepository;

@Service
public class PlayerService {
	private final PlayerRepository playerRepository;
	
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	public List<Player> allPlayers() {
		return playerRepository.findAll();
	}
	
	public Player createPlayer(Player p) {
		return playerRepository.save(p);
	}
	
	public Player findPlayer(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public boolean canAddPlayer(Team team) {
        return team.getPlayers().size() < 9;
    }

}
