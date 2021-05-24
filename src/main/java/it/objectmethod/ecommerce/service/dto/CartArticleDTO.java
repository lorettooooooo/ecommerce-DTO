package it.objectmethod.ecommerce.service.dto;

public class CartArticleDTO {

	private Long id;
	private Integer quantity;
	private String code;
	private String name;
	private Integer price;
	private Long articleId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPrice() {
		return price;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
}