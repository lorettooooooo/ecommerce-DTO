package it.objectmethod.ecommerce.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.service.OrderService;
import it.objectmethod.ecommerce.service.dto.OrderDTO;

@Component
public class ScheduledTasks {

	@Autowired
	OrderService orderService;

	@Scheduled(cron = "${scheduler.cron.expression.lastMonthOrders}")
	private void getLastMonthOrders() {
		List<OrderDTO> lastMonthOrders = orderService.getLastMonthOrders();
		for (OrderDTO orderDTO : lastMonthOrders) {
			String id = orderDTO.getId().toString();
			String date = orderDTO.getDate();
			String orderNumber = orderDTO.getOrderNumber();
			String userId = orderDTO.getUserId().toString();
			System.out.println("ordine " + orderNumber + " ID :" + id + " data :" + date + ", userID :" + userId);
		}
	}
}
