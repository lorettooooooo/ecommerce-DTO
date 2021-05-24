package it.objectmethod.ecommerce.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.mapper.UserMapper;
import it.objectmethod.ecommerce.service.CartService;
import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.OrderService;
import it.objectmethod.ecommerce.service.dto.CartDTO;
import it.objectmethod.ecommerce.service.dto.OrderDTO;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	JWTService jwtServ;

	@Autowired
	CartService cartServ;

	@Autowired
	OrderService orderServ;

	@Autowired
	UserMapper userMapper;

	@RequestMapping("/submitOrder")
	public OrderDTO submitOrder(@RequestHeader("auth-token") String token, HttpServletResponse response) {

		CartDTO cartDto = getCartFromToken(token);
		if (cartDto == null) {
			badRequest(response);
		}
		OrderDTO orderDto = orderServ.setOrderDTO(cartDto);
		return orderDto;
	}

	// metodi privati

	private CartDTO getCartFromToken(String token) {
		UserDTO userDTO = jwtServ.getUserByToken(token);
		Long userId = userDTO.getId();

		CartDTO userCartDto = null;
		userCartDto = cartServ.getUserCart(userId);
		return userCartDto;
	}

	private void badRequest(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

}
