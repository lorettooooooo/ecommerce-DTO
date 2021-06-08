package it.objectmethod.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.mapper.UserMapper;
import it.objectmethod.ecommerce.service.CartService;
import it.objectmethod.ecommerce.service.JWTService;
import it.objectmethod.ecommerce.service.UserService;
import it.objectmethod.ecommerce.service.dto.CartDTO;
import it.objectmethod.ecommerce.service.dto.LoginDTO;
import it.objectmethod.ecommerce.service.dto.PasswordDTO;
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

	@Autowired
	CartService cartService;

	@PostMapping("/login")
	public ResponseEntity<UserDTO> setLoginToken(@RequestBody LoginDTO loginUser) {
		ResponseEntity<UserDTO> ret = null;
		String token = null;
		UserDTO userDTO = userService.loginCheck(loginUser);
		if (userDTO == null) {
			ret = new ResponseEntity<UserDTO>(userDTO, HttpStatus.BAD_REQUEST);

		} else {
			token = jwtSrv.createJWTToken(userDTO);
			userDTO.setToken(token);
			ret = new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}

		return ret;
	}

	@PostMapping("/tokenCheck")
	public ResponseEntity<Boolean> tokenCheck(@RequestHeader("auth-token") String token) {
		ResponseEntity<Boolean> ret = null;
		if (jwtSrv.checkJWTToken(token) == false) {
			ret = new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
		} else {
			ret = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return ret;
	}

	@PostMapping("/changePassword")
	public ResponseEntity<Void> setNewPassword(@RequestHeader("auth-token") String token,
			@RequestBody PasswordDTO passwordDto) {
		ResponseEntity<Void> ret = null;
		LoginDTO loginDto = new LoginDTO();
		String username = jwtSrv.getUserDTOByToken(token).getUsername();
		loginDto.setUsername(username);
		loginDto.setPassword(passwordDto.getOldPassword());
		UserDTO userDto = userService.loginCheck(loginDto);
		if (userDto == null) { // primo controllo: lo user ha messo la vecchia password correttamente
			ret = new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		} else if (passwordDto.getOldPassword().equals(passwordDto.getNewPassword())) {
			// secondo controllo: lo user ha messo pwd vecchia e nuova uguali
			ret = new ResponseEntity<Void>(HttpStatus.NOT_MODIFIED);
		} else {
			userService.setPassword(username, passwordDto.getNewPassword());
			ret = new ResponseEntity<Void>(HttpStatus.OK);
		}
		return ret;
	}

	@PostMapping("/createUserLogin")
	public ResponseEntity<UserDTO> createUser(@RequestBody LoginDTO loginDTO) {
		ResponseEntity<UserDTO> ret = null;
		UserDTO userDto = null;

		Boolean userAlreadyExist = userService.checkUsername(loginDTO.getUsername());
		if (userAlreadyExist) {
			ret = new ResponseEntity<UserDTO>(userDto, HttpStatus.IM_USED);
		} else {
			userDto = userService.setNewUser(loginDTO);
			String token = jwtSrv.createJWTToken(userDto);
			userDto.setToken(token);
			ret = new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
		}

		return ret;
	}

	@PostMapping("/deleteUser")
	public ResponseEntity<Boolean> deleteUser(@RequestHeader("auth-token") String token) {
		ResponseEntity<Boolean> ret = null;
		UserDTO userDto = jwtSrv.getUserDTOByToken(token);
		CartDTO cartDto = cartService.getUserCart(userDto.getId());
		if (cartDto != null) {
			cartService.deleteCart(cartDto.getId());
		}
		userService.deleteUser(userDto);
		ret = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return ret;
	}
}
