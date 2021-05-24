package it.objectmethod.ecommerce.service.dto;

import java.util.List;

public class CartDTO {

	private Long id;
	private Long userId;
	private List<CartArticleDTO> cartArticles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartArticleDTO> getCartArticles() {
		return cartArticles;
	}

	public void setCartArticles(List<CartArticleDTO> cartArticles) {
		this.cartArticles = cartArticles;
	}
}
