package com.snosack.beltexam.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="teams")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Team Name is required.")
	@Size(min=3, message="Team Name must be at least 3 characters.")
	private String name;
	
	@NotNull
	@Min(value=1, message="Skill Level must be between 1 and 5")
	@Max(value=5, message="Skill Level must be between 1 and 5")
	private int skillLevel;
	
	@NotEmpty(message="Game Day is required.")
	@Size(min=3, message="Game Day must be at least 3 characters.")
	private String gameDay;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate(){
		this.updatedAt = new Date();
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="team", fetch = FetchType.LAZY)
    private List<Player> players;
	
	public Team() {}
	
	
	
	public Team(
			@NotEmpty(message = "Team Name is required.") @Size(min = 3, message = "Team Name must be at least 3 characters.") String name,
			@NotNull @Min(value = 1, message = "Skill Level must be between 1 and 5") @Max(value = 5, message = "Skill Level must be between 1 and 5") int skillLevel,
			@NotEmpty(message = "Game Day is required.") @Size(min = 3, message = "Game Day must be at least 3 characters.") String gameDay,
			User user) {
		this.name = name;
		this.skillLevel = skillLevel;
		this.gameDay = gameDay;
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	public String getGameDay() {
		return gameDay;
	}
	public void setGameDay(String gameDay) {
		this.gameDay = gameDay;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	
}
