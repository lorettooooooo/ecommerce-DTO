package it.objectmethod.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.domain.Article;
import it.objectmethod.ecommerce.domain.Cart;
import it.objectmethod.ecommerce.domain.CartArticle;
import it.objectmethod.ecommerce.domain.User;
import it.objectmethod.ecommerce.mapper.CartArticleMapper;
import it.objectmethod.ecommerce.mapper.CartMapper;
import it.objectmethod.ecommerce.repository.ArticleRepository;
import it.objectmethod.ecommerce.repository.CartArticleRepository;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.UserRepository;
import it.objectmethod.ecommerce.service.dto.CartArticleDTO;
import it.objectmethod.ecommerce.service.dto.CartDTO;

@Service
public class CartService {

	Logger logger = LoggerFactory.getLogger(CartService.class);

	@Autowired
	CartRepository cartRepo;

	@Autowired
	ArticleRepository articleRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	CartArticleRepository cartArticleRepo;

	@Autowired
	CartMapper cartMapper;

	@Autowired
	CartArticleMapper cartArticleMapper;

	public CartDTO getUserCart(Long userId) {
		Optional<List<Cart>> userCartListOpt = null;

		CartDTO cartDto = null;
		Cart userCart = null;
		while (userCart == null) {
			userCartListOpt = cartRepo.findByUserId(userId);
			List<Cart> userCartList = userCartListOpt.get();
			if (userCartList.size() == 0) {
				logger.info("creo nuovo carrello per userID " + userId);
				userCart = new Cart();
				Optional<User> userOpt = userRepo.findById(userId);
				userCart.setUser(userOpt.get());
				cartRepo.save(userCart);
				userCartListOpt = cartRepo.findByUserId(userId);
			} else {
				while (userCartList.size() > 1) {
					Cart cart = userCartList.get(0);
					cartRepo.delete(cart);
					cartRepo.flush();
					userCartListOpt = cartRepo.findByUserId(userId);
					userCartList = userCartListOpt.get();
				}
				userCart = userCartList.get(0);
			}
			cartDto = cartMapper.toDto(userCart);
		}

		return cartDto;
	}

	public CartArticleDTO addArticle(Long cartId, Long articleId) {
		CartArticleDTO cartArticleDTO = null;
		Optional<CartArticle> cartArticleOpt = cartArticleRepo.findByCartIdAndArticleId(cartId, articleId);
		if (cartArticleOpt.isEmpty()) { // creo articolo-carrello
			CartArticle cartArticle = new CartArticle();
			Cart cart = cartRepo.findById(cartId).get();
			cartArticle.setCart(cart);
			Article article = articleRepo.findById(articleId).get();
			cartArticle.setArticle(article);
			cartArticle.setQuantity(1);
			cartArticleRepo.save(cartArticle);
			cartArticleDTO = cartArticleMapper.toDto(cartArticle);
		} else { // l'articolo-carrello esiste e va updatato
			CartArticle cartArticle = cartArticleOpt.get();
			Integer oldQuantity = cartArticle.getQuantity();
			cartArticle.setQuantity(oldQuantity + 1);
			cartArticleRepo.save(cartArticle);
			cartArticleDTO = cartArticleMapper.toDto(cartArticleOpt.get());
		}

		return cartArticleDTO;
	}

	@Transactional
	public CartArticleDTO removeArticle(Long cartId, Long articleId) {
		CartArticleDTO cartArticleDTO = null;

		Optional<CartArticle> cartArticleOpt = cartArticleRepo.findByCartIdAndArticleId(cartId, articleId);
		if (cartArticleOpt.isPresent()) {
			logger.info("l'articolo-carrello esiste");

			CartArticle cartArticle = cartArticleOpt.get();
			Integer oldQuantity = cartArticle.getQuantity();
			Integer newQuantity = null;
			if (oldQuantity > 1) { // se è maggiore di 1 rimarrà almeno 1, quindi l'articolo-carrello continua a
									// esistere
				newQuantity = oldQuantity - 1;
				cartArticle.setQuantity(newQuantity);
				cartArticleRepo.save(cartArticle);
				cartArticleDTO = cartArticleMapper.toDto(cartArticle);
			} else { // l'articolo-carrello viene cancellato quando la quantità dovrebbe scendere
						// sotto 1
				logger.info("l'articolo-carrello esiste e va eliminato perché ho 1 solo oggetto, l'ID è "
						+ cartArticle.getId());
				cartArticleRepo.deleteById(cartArticle.getId());
				cartArticleRepo.flush();
			}
		}
		return cartArticleDTO;
	}

	public void deleteCartArticleList(Long cartId, Long articleId) {
		Optional<CartArticle> cartArticleOpt = cartArticleRepo.findByCartIdAndArticleId(cartId, articleId);
		if (cartArticleOpt.isPresent()) {
			CartArticle cartArticle = cartArticleOpt.get();
			cartArticleRepo.delete(cartArticle);
			cartArticleRepo.flush();
		}
	}

	@Transactional
	public void deleteCart(Long cartId) {
		cartArticleRepo.deleteByCartId(cartId);
		cartRepo.deleteById(cartId);
		cartRepo.flush();
	}
}
