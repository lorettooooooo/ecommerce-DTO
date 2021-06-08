package it.objectmethod.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.domain.OrderDetail;
import it.objectmethod.ecommerce.mapper.OrderDetailMapper;
import it.objectmethod.ecommerce.repository.OrderDetailRepository;
import it.objectmethod.ecommerce.service.dto.OrderDetailDTO;

@Service
public class OrderDetailService {

	@Autowired
	OrderDetailRepository orderDetRepo;

	@Autowired
	OrderDetailMapper orderDetMapper;

	public List<OrderDetailDTO> getOrderDetailListByOrderId(Long OrderId) {
		List<OrderDetail> orderDetList = orderDetRepo.findAllByOrderId(OrderId);
		List<OrderDetailDTO> orderDetListDTO = orderDetMapper.toDto(orderDetList);
		return orderDetListDTO;
	}
}
