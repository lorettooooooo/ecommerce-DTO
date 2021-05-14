package it.objectmethod.ecommerce.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.ecommerce.domain.User;
import it.objectmethod.ecommerce.mapper.UserMapper;
import it.objectmethod.ecommerce.repository.UserRepository;
import it.objectmethod.ecommerce.service.dto.LoginDTO;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserMapper userMapper;

	public UserDTO loginCheck(LoginDTO user, HttpServletResponse response) {

		UserDTO userDTO = null;
		if (user == null) {
			badRequest(response);
		} else {
			String username = user.getUsername();
			String password = user.getPassword();
			Optional<User> userOpt = userRepo.findByUsernameAndPassword(username, password);
			if (userOpt.isEmpty()) {
				noContent(response);
			} else {
				userDTO = userMapper.toDto(userOpt.get());

			}

		}

		return userDTO;
	}

	private void badRequest(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	private void noContent(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

}
