package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.domain.CartArticle;
import it.objectmethod.ecommerce.service.dto.CartArticleDTO;

@Mapper(componentModel = "spring")
public interface CartArticleMapper extends EntityMapper<CartArticleDTO, CartArticle> {
	@Override
	@Mapping(source = "article.name", target = "name")
	@Mapping(source = "article.price", target = "price")
	@Mapping(source = "article.code", target = "code")
	@Mapping(source = "article.id", target = "articleId")
	CartArticleDTO toDto(CartArticle entity);

	@Override
	@Mapping(target = "article", ignore = true)
	@Mapping(target = "cart", ignore = true)
	CartArticle toEntity(CartArticleDTO dto);
}
