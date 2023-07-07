package EPICODE.EPIC_BNB.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.UserCreatePayload;
import EPICODE.EPIC_BNB.services.UsersService;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private PasswordEncoder bcrypt;

	@GetMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return usersService.find(page, size, sortBy);

	}

	@GetMapping("/me")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return usersService.findByUsername(username);
	}

	@PutMapping("/me")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public User getCurrentUserAndUpdate(@RequestBody UserCreatePayload body) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return usersService.findByUsernameAndUpdate(username, body);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		usersService.findByIdAndDelete(userId);
	}

}
