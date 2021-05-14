package it.objectmethod.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.domain.Article;
import it.objectmethod.ecommerce.mapper.ArticleMapper;
import it.objectmethod.ecommerce.repository.ArticleRepository;
import it.objectmethod.ecommerce.service.dto.ArticleDTO;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	@Autowired
	private ArticleMapper articleMapper;

	public List<ArticleDTO> showPurchasableArticles() {
		List<ArticleDTO> articleListDto = null;
		List<Article> articleList = articleRepo.findByAvailabilityGreaterThan(0);
		articleListDto = articleMapper.toDto(articleList);
		return articleListDto;
	}
}
