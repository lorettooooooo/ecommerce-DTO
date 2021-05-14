package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.ArticleService;
import it.objectmethod.ecommerce.service.dto.ArticleDTO;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

	@Autowired
	ArticleService articleServ;

	@RequestMapping("/purchasables")
	public List<ArticleDTO> showPurchasableArticles() {
		List<ArticleDTO> purchasableList = new ArrayList<ArticleDTO>();
		purchasableList = articleServ.showPurchasableArticles();
		return purchasableList;
	}
}
