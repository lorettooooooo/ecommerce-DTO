package it.objectmethod.ecommerce.service.dto;

public class ArticleDTO {
	private Long id;
	private Integer availability;
	private String code;
	private String name;
	private Integer price;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public Integer getAvailability() {
		return availability;
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
}
