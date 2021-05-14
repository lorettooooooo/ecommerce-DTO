package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.domain.Article;
import it.objectmethod.ecommerce.service.dto.ArticleDTO;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
	@Override
	ArticleDTO toDto(Article entity);

	@Override
	@Mapping(target = "cartList", ignore = true)
	Article toEntity(ArticleDTO dto);
}
