package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;

import it.objectmethod.ecommerce.domain.Article;
import it.objectmethod.ecommerce.service.dto.ArticleDTO;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
	@Override
	ArticleDTO toDto(Article entity);

	@Override
	Article toEntity(ArticleDTO dto);
}
