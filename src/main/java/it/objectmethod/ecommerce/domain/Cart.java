package it.objectmethod.ecommerce.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "carrello")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrello")
	private Long id;

	@JsonIgnore
	@JoinColumn(name = "id_utente")
	@ManyToOne
	private User user;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "carrello_dettaglio", joinColumns = @JoinColumn(name = "id_carrello", referencedColumnName = "id_carrello"), inverseJoinColumns = @JoinColumn(name = "id_articolo", referencedColumnName = "id_articolo"))
	private List<Article> articles;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setCartArticles(List<CartArticle> cartArticles) {
		this.cartArticles = cartArticles;
	}

	public List<CartArticle> getCartArticles() {
		return cartArticles;
	}
}
