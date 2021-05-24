package it.objectmethod.ecommerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riga_ordine")
public class OrderDetail {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_riga_ordine")
	@Id
	private Long id;

	@Column(name = "quantita")
	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "id_ordine")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "id_articolo")
	private Article article;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
}