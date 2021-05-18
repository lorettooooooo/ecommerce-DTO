package it.objectmethod.ecommerce.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.domain.User;
import it.objectmethod.ecommerce.mapper.UserMapper;
import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.UserService;
import it.objectmethod.ecommerce.service.dto.LoginDTO;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	JWTService jwtSrv;

	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

//	@RequestMapping("/login")
//	public UserDTO loginCheck(@RequestBody LoginDTO user, HttpServletResponse response) {
//		UserDTO userDTO = userService.loginCheck(user, response);
//		return userDTO;
//	}

	@RequestMapping("/login")
	public String loginToken(@RequestBody LoginDTO loginUser, HttpServletResponse response) {
		UserDTO userDTO = userService.loginCheck(loginUser, response);
		User user = userMapper.toEntity(userDTO);

		String token = jwtSrv.createJWTToken(user);
		return token;
	}
}
