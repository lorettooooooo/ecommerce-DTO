package it.objectmethod.ecommerce.service.dto;

import java.util.List;

public class CartDTO {

	private Integer id;
	private Integer userId;
	private List<CartArticleDTO> cartArticles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<CartArticleDTO> getCartArticles() {
		return cartArticles;
	}

	public void setCartArticles(List<CartArticleDTO> cartArticles) {
		this.cartArticles = cartArticles;
	}
}
