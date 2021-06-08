package it.objectmethod.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	public Optional<List<Cart>> findByUserId(Long userId);

}
