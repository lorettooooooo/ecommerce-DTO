package it.objectmethod.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.domain.Article;

@Component
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	public List<Article> findByAvailabilityGreaterThan(Integer zero);
}
