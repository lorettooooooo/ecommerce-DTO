package it.objectmethod.ecommerce.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.CartService;
import it.objectmethod.ecommerce.service.dto.CartArticleDTO;
import it.objectmethod.ecommerce.service.dto.CartDTO;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartServ;

	@RequestMapping("/myCart")
	public CartDTO showUserCart(@RequestParam("userId") Integer userId, HttpServletResponse response) {
		CartDTO userCart = null;
		userCart = cartServ.showUserCart(userId, response);
		return userCart;
	}

	@RequestMapping("/{articleId}/addArticle")
	public CartArticleDTO addArticle(@RequestParam("cartId") Integer cartId,
			@PathVariable("articleId") Integer articleId, HttpServletResponse response) {
		CartArticleDTO userCart = cartServ.addArticle(cartId, articleId, response);
		return userCart;
	}
}
