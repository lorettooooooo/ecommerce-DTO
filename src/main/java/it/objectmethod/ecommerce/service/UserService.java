package it.objectmethod.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

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

	public UserDTO loginCheck(LoginDTO loginUser) {

		UserDTO userDTO = null;
		String username = loginUser.getUsername();
		String password = loginUser.getPassword();
		Optional<User> userOpt = userRepo.findByUsernameAndPassword(username, password);
		if (userOpt.isPresent()) {
			userDTO = userMapper.toDto(userOpt.get());
		}
		return userDTO;
	}

	public Long getUserIdFromToken(String token) {
		Algorithm alg = Algorithm.HMAC256("Famme-vede-er-carrello");
		Long userId = null;
		try {
			JWTVerifier ver = JWT.require(alg).build();
			DecodedJWT decoded = ver.verify(token);
			userId = decoded.getClaim("user_id").asLong();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userId;
	}

	public boolean checkUsername(String username) {
		boolean doesAlreadyExist = false;
		Optional<User> userOpt = userRepo.findByUsername(username);
		if (userOpt.isPresent()) {
			doesAlreadyExist = true;
		}
		return doesAlreadyExist;
	}

	public UserDTO setUsername(Long userId, String newUsername) {
		Optional<User> userOpt = userRepo.findById(userId);
		User user = userOpt.get();
		user.setUsername(newUsername);
		userRepo.save(user);
		UserDTO userDTO = userMapper.toDto(user);
		return userDTO;
	}

	public void setPassword(String username, String password) {
		Optional<User> userOpt = userRepo.findByUsername(username);
		User user = userOpt.get();
		user.setPassword(password);
		userRepo.save(user);
	}

	public UserDTO setNewUser(LoginDTO loginDto) {
		User user = new User();
		user.setPassword(loginDto.getPassword());
		user.setUsername(loginDto.getUsername());
		userRepo.save(user);
		UserDTO userDto = userMapper.toDto(user);
		return userDto;
	}

	public void deleteUser(UserDTO userDto) {
		Optional<User> userOpt = userRepo.findById(userDto.getId());
		userRepo.delete(userOpt.get());
	}

}
