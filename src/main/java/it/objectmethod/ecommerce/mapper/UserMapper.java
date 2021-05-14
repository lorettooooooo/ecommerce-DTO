package it.objectmethod.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.ecommerce.domain.User;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
	@Override
	UserDTO toDto(User entity);

	@Override
	@Mapping(target = "password", ignore = true)
	User toEntity(UserDTO dto);
}
