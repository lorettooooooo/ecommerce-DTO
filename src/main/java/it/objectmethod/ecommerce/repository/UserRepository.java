package it.objectmethod.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.domain.User;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUsernameAndPassword(String username, String password);
}
