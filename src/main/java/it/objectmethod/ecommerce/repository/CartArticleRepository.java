package it.objectmethod.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.domain.CartArticle;

@Component
public interface CartArticleRepository extends JpaRepository<CartArticle, Long> {

	public Optional<CartArticle> findByCartIdAndArticleId(Integer cartId, Integer articleId);

//	@Override
//	@Modifying(clearAutomatically = true)
//	@Transactional
//	public void deleteById(Integer Id);
}
