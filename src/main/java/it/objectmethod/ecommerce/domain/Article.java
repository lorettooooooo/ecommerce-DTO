package it.objectmethod.ecommerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articolo")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_articolo")
	private Long id;

	@Column(name = "disponibilita")
	private Integer availability;

	@Column(name = "codice_articolo")
	private String code;

	@Column(name = "nome_articolo")
	private String name;

	@Column(name = "prezzo_unitario")
	private Integer price;

	@Column(name = "image")
	private String image;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
