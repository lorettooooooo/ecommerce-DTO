package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.domain.Cart;
import it.objectmethod.ecommerce.service.dto.CartDTO;

@Mapper(componentModel = "spring", uses = { CartArticleMapper.class })
public interface CartMapper extends EntityMapper<CartDTO, Cart> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	CartDTO toDto(Cart entity);

	@Override
	Cart toEntity(CartDTO dto);
}
