package it.objectmethod.ecommerce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.domain.Cart;
import it.objectmethod.ecommerce.domain.CartArticle;
import it.objectmethod.ecommerce.domain.Order;
import it.objectmethod.ecommerce.domain.OrderDetail;
import it.objectmethod.ecommerce.domain.User;
import it.objectmethod.ecommerce.mapper.OrderMapper;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.OrderDetailRepository;
import it.objectmethod.ecommerce.repository.OrderRepository;
import it.objectmethod.ecommerce.service.dto.CartDTO;
import it.objectmethod.ecommerce.service.dto.OrderDTO;

@Service
public class OrderService {

	Logger logger = LoggerFactory.getLogger(CartService.class);

	@Autowired
	CartRepository cartRepo;

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	OrderDetailRepository orderDetailRepo;

	@Autowired
	OrderMapper orderMapper;

	public OrderDTO setOrderDTO(CartDTO cartDto) {
		Long cartId = cartDto.getId();
		// estraggo l'entità carrello per prendere gli articoli
		Optional<Cart> cartOpt = cartRepo.findById(cartId);
		Cart cart = cartOpt.get();
		User user = cart.getUser();
		Order order = new Order();
		orderRepo.save(order);
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		// creo lista di articoli per l'ordine estraendola dalla lista di cart articles
		for (CartArticle cartArticle : cart.getCartArticles()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setArticle(cartArticle.getArticle());
			orderDetail.setQuantity(cartArticle.getQuantity());
			orderDetailRepo.save(orderDetail);
			orderDetailList.add(orderDetail);
		}
		// setto tutte le variabili necessarie nell'entità "order"
		order.setUserId(user.getId());
		LocalDate localDate = LocalDate.now();
		String stringDate = localDate.toString();
		order.setDate(stringDate);
		String code = "A000" + order.getId();
		order.setOrderNumber(code);
		order.setOrderDetails(orderDetailList);
		order.setOrderDetails(orderDetailList);
		System.out.println(order.getId());
		// salvo l'entità order e mi genero un DTO da ritornare
		orderRepo.save(order);
		OrderDTO orderDto = orderMapper.toDto(order);
		return orderDto;
	}

	public List<OrderDTO> getOrdersByUserId(Long userId) {
		List<Order> orderList = orderRepo.findByUserId(userId);
		List<OrderDTO> orderListDTO = orderMapper.toDto(orderList);
		return orderListDTO;
	}

//	public OrderDTO getOrderById(Long orderId) {
//		OrderDTO orderDTO = null;
//		
//		return orderDTO;
//	}

	public List<OrderDTO> getLastMonthOrders() {

		String today = LocalDate.now().toString();
		String thisMonthDate = today.substring(0, 7);
		Integer thisMonthInt = Integer.parseInt(thisMonthDate.substring(5));
		String lastMonth = "";
		String lastMonthDate = "";
		if (thisMonthInt == 01) {
			Integer lastYearInt = Integer.parseInt(thisMonthDate.substring(0, 4)) - 1;
			String lastYear = lastYearInt.toString();
			lastMonth = "12";
			lastMonthDate = lastYear + "-" + lastMonth;
		} else {
			Integer lastMonthInt = thisMonthInt - 1;
			if (lastMonthInt < 10) {
				lastMonth = "0";
			}
			lastMonth = lastMonth + lastMonthInt.toString();
			lastMonthDate = thisMonthDate.substring(0, 4) + "-" + lastMonth;
		}
		List<Order> orderList = orderRepo.findByDateStartsWith(lastMonthDate);
		System.out.println(lastMonthDate);
		List<OrderDTO> orderListDTO = orderMapper.toDto(orderList);
		return orderListDTO;
	}
}
