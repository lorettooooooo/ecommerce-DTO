package it.objectmethod.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.repository.CartArticleRepository;

@Service
public class CartArticleService {

	@Autowired
	CartArticleRepository cartArtRepo;

	public void deleteByCartId(Long cartId) {
		cartArtRepo.deleteByCartId(cartId);
	}
}
