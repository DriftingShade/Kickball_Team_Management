package com.snosack.beltexam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.snosack.beltexam.models.Player;
import com.snosack.beltexam.models.Team;
import com.snosack.beltexam.models.User;
import com.snosack.beltexam.services.PlayerService;
import com.snosack.beltexam.services.TeamService;
import com.snosack.beltexam.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class TeamController {
	@Autowired
	UserService userService;
	
	@Autowired
	TeamService tServ;
	
	@Autowired
	PlayerService playerService;
	
//	Team Routes!
	
	@GetMapping("/teams/new")
	public String newForm(@ModelAttribute("team") Team team, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		User user = userService.findUser(userId);
		model.addAttribute("user", user);
		model.addAttribute("team", team);
		return "newTeam.jsp";
	}
	
	@PostMapping("/teams/new")
	public String create(@Valid @ModelAttribute("team") Team team, BindingResult result, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			User user = userService.findUser(userId);
			model.addAttribute("user", user);
			model.addAttribute("team", team);
			return "newTeam.jsp";
		} else {
			User user = userService.findUser(userId);
			team.setUser(user);
			tServ.newTeam(team);
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/teams/{id}")
	public String view(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}
		Player player = new Player();
		model.addAttribute("player", player);
		User user = userService.findUser(userId);
		model.addAttribute("user", user);
		Team team = tServ.findTeam(id);
		model.addAttribute(team);
		return "oneTeam.jsp";
	}
	
	@GetMapping("/teams/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}
		User user = userService.findUser(userId);
		Team team = tServ.findTeam(id);
		if (!team.getUser().getId().equals(userId)) {
			return "redirect:/dashboard";
		}
		model.addAttribute("user", user);
		model.addAttribute("team", team);
		return "editTeam.jsp";
	}
	
	@PutMapping("/update/teams/{id}")
	public String updateTeam(@PathVariable("id") Long id, @Valid @ModelAttribute("team") Team team, BindingResult result, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			User user = userService.findUser(userId);
			model.addAttribute("user", user);
			model.addAttribute("team", team);
			return "editTeam.jsp";
		} else {
			User user = userService.findUser(userId);
			team.setUser(user);
			tServ.updateTeam(team);
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping(value="/teams/{teamId}/delete", method=RequestMethod.DELETE)
	public String delete(@PathVariable("teamId") Long teamId, HttpSession session) {
	    Long userId = (Long) session.getAttribute("userId");
	    if (userId == null) {
	        return "redirect:/";
	    }

	    Team team = tServ.findTeam(teamId);
	    if (team == null) {
	        return "redirect:/dashboard";
	    }
	    if (!team.getUser().getId().equals(userId)) {
	        return "redirect:/dashboard";
	    }
	    tServ.deleteTeam(teamId);
	    return "redirect:/dashboard";
	}
	
//	Player Routes!
	
	@PostMapping("/teams/{teamId}/addPlayer")
	public String addPlayer(@PathVariable("teamId") Long teamId, @ModelAttribute("player") @Valid Player player, BindingResult result, HttpSession session, Model model) {
	    Long userId = (Long) session.getAttribute("userId");
	    Team team = tServ.findTeam(teamId);
	    User user = userService.findUser(userId);
	    if (userId == null) {
	        return "redirect:/";
	    }
	    if (team == null) {
	        return "redirect:/dashboard";
	    }

	    if (result.hasErrors()) {
	        model.addAttribute("team", team);
	        model.addAttribute("user", userService.findUser(userId));
	        return "oneTeam.jsp";
	    }

	    if (!playerService.canAddPlayer(team)) {
	        model.addAttribute("team", team);
	        model.addAttribute("error", "The team is full!");
	        model.addAttribute("user", userService.findUser(userId));
	        return "oneTeam.jsp";
	    }

	    player.setTeam(team);
	    playerService.createPlayer(player);

	    return "redirect:/teams/" + teamId;
	}
}