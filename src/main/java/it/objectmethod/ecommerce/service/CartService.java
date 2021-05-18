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
		if (userId == null) {
			badRequest(response);
		}
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
			if (userCart.isEmpty()) { // creo carrello
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

	public CartArticleDTO removeArticle(Integer cartId, Integer articleId, HttpServletResponse response) {
		CartArticleDTO cartArticleDTO = null;
		if (cartId == null || articleId == null) {
			System.out.println("non specifico il carrello");
			badRequest(response);
		} else {
			Optional<CartArticle> cartArticleOpt = cartArticleRepo.findByCartIdAndArticleId(cartId, articleId);
			if (!cartArticleOpt.isPresent()) {
				noContent(response);
			} else {
				System.out.println("il carrello esiste");
				CartArticle cartArticle = cartArticleOpt.get();
				Integer oldQuantity = cartArticle.getQuantity();
				Integer newQuantity = null;
				if (oldQuantity > 1) { // se è maggiore di 1 rimarrà almeno 1, quindi il carrello continua a esistere
					newQuantity = oldQuantity - 1;
					cartArticle.setQuantity(newQuantity);
					cartArticleRepo.save(cartArticle);
					cartArticleDTO = cartArticleMapper.toDto(cartArticle);
				} else { // il carrello viene cancellato quando la quantità dovrebbe scendere sotto 1
					System.out.println(
							"il carrello esiste e va eliminato perché ho 1 oggetto, l'ID è " + cartArticle.getId());
					cartArticleRepo.deleteById(cartArticle.getId());
					cartArticleDTO = cartArticleMapper.toDto(cartArticle);
				}
			}
		}

		return cartArticleDTO;
	}

	// metodi privati
	private void badRequest(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	private void noContent(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
