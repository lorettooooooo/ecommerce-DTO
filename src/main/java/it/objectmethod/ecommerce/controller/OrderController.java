package it.objectmethod.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.mapper.UserMapper;
import it.objectmethod.ecommerce.service.CartService;
import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.OrderDetailService;
import it.objectmethod.ecommerce.service.OrderService;
import it.objectmethod.ecommerce.service.dto.CartDTO;
import it.objectmethod.ecommerce.service.dto.OrderDTO;
import it.objectmethod.ecommerce.service.dto.OrderDetailDTO;
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
	OrderDetailService orderDetServ;

	@Autowired
	UserMapper userMapper;

	@RequestMapping("/submitOrder")
	public ResponseEntity<OrderDTO> submitOrder(@RequestHeader("auth-token") String token) {
		ResponseEntity<OrderDTO> ret = null;
		UserDTO userDTO = jwtServ.getUserDTOByToken(token);
		Long userId = userDTO.getId();
		CartDTO cartDto = cartServ.getUserCart(userId);
		OrderDTO orderDto = orderServ.setOrderDTO(cartDto);
		Long cartId = cartDto.getId();
		cartServ.deleteCart(cartId);
		ret = new ResponseEntity<OrderDTO>(orderDto, HttpStatus.OK);
		return ret;
	}

	@RequestMapping("/myOrders")
	public ResponseEntity<List<OrderDTO>> getOrderList(@RequestHeader("auth-token") String token) {
		ResponseEntity<List<OrderDTO>> ret = null;
		UserDTO userDTO = jwtServ.getUserDTOByToken(token);
		Long userId = userDTO.getId();
		List<OrderDTO> orderListDTO = orderServ.getOrdersByUserId(userId);
		if (orderListDTO == null) {
			ret = new ResponseEntity<List<OrderDTO>>(HttpStatus.NO_CONTENT);
		} else {
			ret = new ResponseEntity<List<OrderDTO>>(orderListDTO, HttpStatus.OK);
		}
		return ret;
	}

	@RequestMapping("/orderDetail")
	public ResponseEntity<List<OrderDetailDTO>> getOrderDetails(@RequestHeader("auth-token") String token,
			@RequestParam Long orderId) {
		ResponseEntity<List<OrderDetailDTO>> ret = null;
		List<OrderDetailDTO> orderDetailListDTO = orderDetServ.getOrderDetailListByOrderId(orderId);
		if (orderDetailListDTO == null) {
			ret = new ResponseEntity<List<OrderDetailDTO>>(HttpStatus.NO_CONTENT);
		} else {
			ret = new ResponseEntity<List<OrderDetailDTO>>(orderDetailListDTO, HttpStatus.OK);
		}
		return ret;
	}
}
