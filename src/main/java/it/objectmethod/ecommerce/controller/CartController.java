package it.objectmethod.ecommerce.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.service.CartService;
import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.dto.CartArticleDTO;
import it.objectmethod.ecommerce.service.dto.CartDTO;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	Logger logger = LoggerFactory.getLogger(CartService.class);

	@Autowired
	private CartService cartServ;

	@Autowired
	private JWTService jwtServ;

	@RequestMapping("/myCart")
	public ResponseEntity<CartDTO> showUserCart(@RequestHeader("auth-token") String token) {
		ResponseEntity<CartDTO> ret = null;
		CartDTO userCartDto = getCartFromToken(token);
		if (userCartDto == null) {
			ret = new ResponseEntity<CartDTO>(HttpStatus.NO_CONTENT);
		} else {
			ret = new ResponseEntity<CartDTO>(userCartDto, HttpStatus.FOUND);
		}
		return ret;
	}

	@RequestMapping("/addArticle")
	public ResponseEntity<CartArticleDTO> addArticle(@RequestHeader("auth-token") String token,
			@RequestParam("articleId") Long articleId) {
		ResponseEntity<CartArticleDTO> ret = null;
		Long cartId = getCartFromToken(token).getId();
		if (cartId == null || articleId == null) {
			logger.error("non specifico carrello o articolo");
			ret = new ResponseEntity<CartArticleDTO>(HttpStatus.BAD_REQUEST);
		}
		CartArticleDTO cartArticleDTO = cartServ.addArticle(cartId, articleId);
		if (cartArticleDTO == null) {
			ret = new ResponseEntity<CartArticleDTO>(HttpStatus.NO_CONTENT);
		} else {
			ret = new ResponseEntity<CartArticleDTO>(cartArticleDTO, HttpStatus.FOUND);
		}
		return ret;
	}

	// non cancellano da DB

	@RequestMapping("/removeArticle")
	public ResponseEntity<CartArticleDTO> removeArticle(@RequestHeader("auth-token") String token,
			@RequestParam("articleId") Long articleId) {
		ResponseEntity<CartArticleDTO> ret = null;
		Long cartId = getCartFromToken(token).getId();
		if (cartId == null || articleId == null) {
			logger.error("non specifico carrello o articolo");
			ret = new ResponseEntity<CartArticleDTO>(HttpStatus.BAD_REQUEST);
		}
		CartArticleDTO userCart = cartServ.removeArticle(cartId, articleId);
		if (userCart == null) {
			ret = new ResponseEntity<CartArticleDTO>(HttpStatus.NO_CONTENT);
		} else {
			ret = new ResponseEntity<CartArticleDTO>(userCart, HttpStatus.FOUND);
		}
		return ret;
	}

	@RequestMapping("/deleteArticleList")
	public void deleteArticleList(@RequestHeader("auth-token") String token, @RequestParam("articleId") Long articleId,
			HttpServletResponse response) {
		Long cartId = getCartFromToken(token).getId();
		if (cartId == null || articleId == null) {
			logger.error("non specifico carrello o articolo");
			badRequest(response);
		} else {
			cartServ.deleteArticleList(cartId, articleId);
		}
	}

	@RequestMapping("/removeCart")
	public void deleteCart(@RequestHeader("auth-token") String token, HttpServletResponse response) {
		Long cartId = getCartFromToken(token).getId();
		if (cartId == null) {
			logger.error("non specifico il carrello");
		} else {
			cartServ.deleteCart(cartId);
		}
	}

	// metodi privati

	private CartDTO getCartFromToken(String token) {
		UserDTO userDTO = jwtServ.getUserByToken(token);
		Long userId = userDTO.getId();

		CartDTO userCartDto = null;
		userCartDto = cartServ.getUserCart(userId);
		return userCartDto;
	}

	private void badRequest(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}
