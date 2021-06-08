package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<ArticleDTO>> showPurchasableArticles() {
		ResponseEntity<List<ArticleDTO>> ret = null;
		List<ArticleDTO> purchasableList = new ArrayList<ArticleDTO>();
		purchasableList = articleServ.showPurchasableArticles();
		if (purchasableList == null) {
			ret = new ResponseEntity<List<ArticleDTO>>(HttpStatus.NO_CONTENT);
		} else {
			ret = new ResponseEntity<List<ArticleDTO>>(purchasableList, HttpStatus.OK);
		}

		return ret;
	}
}
