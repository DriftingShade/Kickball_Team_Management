package com.snosack.beltexam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snosack.beltexam.models.Team;
import com.snosack.beltexam.repositories.PlayerRepository;
import com.snosack.beltexam.repositories.TeamRepository;

@Service
public class TeamService {
	@Autowired
	TeamRepository tRepo;
	
	@Autowired
	PlayerRepository playerRepository;
	
	public List<Team> allTeams() {
		return tRepo.findAll();
	}
	
	public Team newTeam(Team team) {
		return tRepo.save(team);
	}
	
	public Team findTeam(Long id) {
		Optional<Team> team = tRepo.findById(id);
		if (team.isPresent()) {
			return team.get();
		} else {
			return null;
		}
	}
	
	public void deleteTeam(Long id) {
        Team team = findTeam(id);
        if (team != null) {
            playerRepository.deleteAll(team.getPlayers());
           tRepo.deleteById(id);
        }
    }
	
	public Team updateTeam(Team team) {
		return tRepo.save(team);
	}

}
