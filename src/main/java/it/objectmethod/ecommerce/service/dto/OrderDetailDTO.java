package it.objectmethod.ecommerce.service.dto;

public class OrderDetailDTO {

	private Long id;
	private Long articleId;
	private String articleName;
	private Integer quantity;
	private Integer articlePrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getArticlePrice() {
		return articlePrice;
	}

	public void setArticlePrice(Integer articlePrice) {
		this.articlePrice = articlePrice;
	}
}
