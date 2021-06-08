package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.domain.Order;
import it.objectmethod.ecommerce.service.dto.OrderDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

	@Override
	OrderDTO toDto(Order entity);

	@Override
	@Mapping(target = "orderDetails", ignore = true)
	Order toEntity(OrderDTO dto);
}
