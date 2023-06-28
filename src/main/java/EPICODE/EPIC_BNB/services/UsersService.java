package EPICODE.EPIC_BNB.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import EPICODE.EPIC_BNB.entities.Role;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.entities.payload.UserCreatePayload;
import EPICODE.EPIC_BNB.exceptions.BadRequestException;
import EPICODE.EPIC_BNB.exceptions.NotFoundException;
import EPICODE.EPIC_BNB.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepo;

	public User create(UserCreatePayload u) {
		usersRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " already in use!");
		});
		User newUser = new User(u.getName(), u.getSurname(), u.getUsername(), u.getEmail(), u.getPassword());
		newUser.setRole(Role.USER);
		return usersRepo.save(newUser);
	}

	public Page<User> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return usersRepo.findAll(pageable);
	}

	public User findById(UUID id) throws NotFoundException {
		return usersRepo.findById(id).orElseThrow(() -> new NotFoundException("Utete con Id:" + id + " non trovato!!"));
	}

	public User findByEmail(String email) throws NotFoundException {
		return usersRepo.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utete con email:" + email + "non trovato!!"));
	}

	public User findByUsername(String username) throws NotFoundException {
		return usersRepo.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Utete:" + username + "non trovato!!"));
	}

	public User findByUsernameAndUpdate(String username, UserCreatePayload u) {
		User user = usersRepo.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Utete:" + username + "non trovato!!"));
		user.setName(u.getName());
		user.setSurname(u.getSurname());
		user.setUsername(u.getUsername());
		user.setEmail(u.getEmail());

		return usersRepo.save(user);
	}

	public User findByIdAndUpdate(UUID id, UserCreatePayload u) {
		User found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessun utente trovato con id: " + id);
		} else {
			found.setId(id);
			found.setName(u.getName());
			found.setSurname(u.getSurname());
			found.setUsername(u.getUsername());
			found.setEmail(u.getEmail());
			usersRepo.save(found);
			return found;
		}

	}

	public void findByIdAndDelete(UUID id) {
		User found = this.findById(id);
		if (found == null) {
			throw new NotFoundException("Nessun utente trovato con id: " + id);
		} else
			usersRepo.delete(found);

	}

}
