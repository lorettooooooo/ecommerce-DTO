package it.objectmethod.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.domain.CartArticle;

@Repository
public interface CartArticleRepository extends JpaRepository<CartArticle, Long> {

	public Optional<CartArticle> findByCartIdAndArticleId(Long cartId, Long articleId);

	public void deleteByCartId(Long cartId);

}
