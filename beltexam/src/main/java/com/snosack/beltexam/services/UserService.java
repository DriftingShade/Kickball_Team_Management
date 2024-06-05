package com.snosack.beltexam.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.snosack.beltexam.models.LoginUser;
import com.snosack.beltexam.models.User;
import com.snosack.beltexam.repositories.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public User register(User newUser, BindingResult result) {
		Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
		if (potentialUser.isPresent()) {
			result.rejectValue("email", "unique", "Email already exists");
		}

		if (!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "Matches", "Passwords must match");
		}

		if (result.hasErrors()) {
			return null;
		} else {
			String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(hashedPassword);
			return userRepo.save(newUser);
		}
	}

	public User login(LoginUser newLoginObject, BindingResult result) {
		Optional<User> optionalUser = userRepo.findByEmail(newLoginObject.getEmail());
		if (optionalUser.isEmpty()) {
			result.rejectValue("email", "NotFound", "Invalid email/password");
			return null;
		}

		User user = optionalUser.get();

		if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
			result.rejectValue("password", "Incorrect", "Invalid email/password");
			return null;
		}

		return user;
	}

	public User findUser(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
}