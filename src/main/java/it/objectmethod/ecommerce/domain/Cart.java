package it.objectmethod.ecommerce.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "carrello")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrello")
	private Long id;

	@JoinColumn(name = "id_utente")
	@ManyToOne
	private User user;

	@OneToMany
	@JoinColumn(name = "id_carrello")
	private List<CartArticle> cartArticles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCartArticles(List<CartArticle> cartArticles) {
		this.cartArticles = cartArticles;
	}

	public List<CartArticle> getCartArticles() {
		return cartArticles;
	}
}
