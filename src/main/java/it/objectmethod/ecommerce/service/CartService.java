package it.objectmethod.ecommerce.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.domain.Article;
import it.objectmethod.ecommerce.domain.Cart;
import it.objectmethod.ecommerce.domain.CartArticle;
import it.objectmethod.ecommerce.mapper.CartArticleMapper;
import it.objectmethod.ecommerce.mapper.CartMapper;
import it.objectmethod.ecommerce.repository.ArticleRepository;
import it.objectmethod.ecommerce.repository.CartArticleRepository;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.service.dto.CartArticleDTO;
import it.objectmethod.ecommerce.service.dto.CartDTO;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepo;

	@Autowired
	ArticleRepository articleRepo;

	@Autowired
	CartArticleRepository cartArticleRepo;

	@Autowired
	CartMapper cartMapper;

	@Autowired
	CartArticleMapper cartArticleMapper;

	public CartDTO showUserCart(Integer userId, HttpServletResponse response) {
		CartDTO cartDto = null;
		Optional<Cart> userCartOpt = cartRepo.findByUserId(userId);
		if (userCartOpt.isEmpty()) {
			noContent(response);
		} else {
			Cart userCart = userCartOpt.get();
			cartDto = cartMapper.toDto(userCart);
		}
		return cartDto;
	}

	public CartArticleDTO addArticle(Integer cartId, Integer articleId, HttpServletResponse response) {
		CartArticleDTO cartArticleDTO = null;
		if (cartId == null || articleId == null) {
			badRequest(response);
		} else {
			Optional<CartArticle> userCart = cartArticleRepo.findByCartIdAndArticleId(cartId, articleId);
			if (userCart.isPresent()) { // creo carrello
				CartArticle cartArticle = new CartArticle();
				Cart cart = cartRepo.findById(cartId).get();
				cartArticle.setCart(cart);
				Article article = articleRepo.findById(articleId).get();
				cartArticle.setArticle(article);
				cartArticle.setQuantity(1);
				cartArticleRepo.save(cartArticle);
				cartArticleDTO = cartArticleMapper.toDto(cartArticle);
			} else { // il carrello esiste e va updatato
				CartArticle cartArticle = userCart.get();
				Integer oldQuantity = cartArticle.getQuantity();
				cartArticle.setQuantity(oldQuantity + 1);
				cartArticleRepo.save(cartArticle);
				cartArticleDTO = cartArticleMapper.toDto(userCart.get());
			}

		}
		return cartArticleDTO;
	}

	private void badRequest(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	private void noContent(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
