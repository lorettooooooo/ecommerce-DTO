package it.objectmethod.ecommerce.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "articolo")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_articolo")
	private Integer id;

	@Column(name = "disponibilita")
	private Integer availability;

	@Column(name = "codice_articolo")
	private String code;

	@Column(name = "nome_articolo")
	private String name;

	@Column(name = "prezzo_unitario")
	private Integer price;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "carrello_dettaglio", joinColumns = @JoinColumn(name = "id_articolo", referencedColumnName = "id_articolo"), inverseJoinColumns = @JoinColumn(name = "id_carrello", referencedColumnName = "id_carrello"))
	private List<Cart> cartList;

	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "id_articolo")
	private List<CartArticle> cartArticlesList;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
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

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public List<Cart> getCartList() {
		return cartList;
	}
}
