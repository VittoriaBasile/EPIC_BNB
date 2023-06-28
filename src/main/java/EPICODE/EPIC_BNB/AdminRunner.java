package EPICODE.EPIC_BNB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import EPICODE.EPIC_BNB.entities.Role;
import EPICODE.EPIC_BNB.entities.User;
import EPICODE.EPIC_BNB.repositories.UsersRepository;

@Component
public class AdminRunner implements CommandLineRunner {

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	private PasswordEncoder bcrypt;

	private static String name;
	private static String surname;
	private static String username;
	private static String email;
	private static String password;

	@Value("${user.admin.nome}")
	public void setNome(String name) {
		this.name = name;
	}

	@Value("${user.admin.cognome}")
	public void setCognome(String surname) {
		this.surname = surname;
	}

	@Value("${user.admin.username}")
	public void setUsername(String username) {
		this.username = username;
	}

	@Value("${user.admin.email}")
	public void setEmail(String email) {
		this.email = email;
	}

	@Value("${user.admin.password}")
	public void setPassword(String password) {
		this.password = password;
	}

	public void createAdmin() {

		if (!usersRepo.findByEmail(email).isPresent()) {

			User userAdmin = new User(name, surname, username, email, password);
			userAdmin.setPassword(bcrypt.encode(password));

			userAdmin.setRole(Role.ADMIN);
			usersRepo.save(userAdmin);
		}

	}

	@Override
	public void run(String... args) throws Exception {

		createAdmin();
	}

}
