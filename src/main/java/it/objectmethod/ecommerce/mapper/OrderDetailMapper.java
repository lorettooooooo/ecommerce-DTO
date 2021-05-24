package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.domain.OrderDetail;
import it.objectmethod.ecommerce.service.dto.OrderDetailDTO;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper extends EntityMapper<OrderDetailDTO, OrderDetail> {

	@Override
	@Mapping(source = "article.id", target = "articleId")
	@Mapping(source = "article.name", target = "articleName")
	OrderDetailDTO toDto(OrderDetail entity);

	@Override
	@Mapping(target = "order", ignore = true)
	@Mapping(target = "article", ignore = true)
	OrderDetail toEntity(OrderDetailDTO dto);
}
