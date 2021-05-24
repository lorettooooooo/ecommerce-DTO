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
@Table(name = "ordine")
public class Order {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ordine")
	@Id
	private Long id;

	@Column(name = "numero_ordine")
	private String orderNumber;

	@Column(name = "data_ordine")
	private String date;

	@ManyToOne
	@JoinColumn(name = "id_utente")
	private User user;

	@OneToMany
	@JoinColumn(name = "id_ordine")
	private List<OrderDetail> orderDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}