package it.objectmethod.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsernameAndPassword(String username, String password);
}
