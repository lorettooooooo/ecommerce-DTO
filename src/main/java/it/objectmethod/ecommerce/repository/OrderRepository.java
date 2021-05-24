package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
