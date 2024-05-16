package br.com.anthonycruz.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by username " + username);
		var user = repository.findByUserName(username);

		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}
}
