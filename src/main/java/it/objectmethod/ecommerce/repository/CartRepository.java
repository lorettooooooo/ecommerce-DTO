package it.objectmethod.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.domain.Cart;

@Component
public interface CartRepository extends JpaRepository<Cart, Integer> {

	public Optional<Cart> findByUserId(Integer userId);
}
