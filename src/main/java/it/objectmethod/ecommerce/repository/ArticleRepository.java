package it.objectmethod.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	public List<Article> findByAvailabilityGreaterThan(Integer zero);
}
