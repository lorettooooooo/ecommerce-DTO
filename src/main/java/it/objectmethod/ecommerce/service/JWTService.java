package it.objectmethod.ecommerce.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import it.objectmethod.ecommerce.domain.User;
import it.objectmethod.ecommerce.mapper.UserMapper;
import it.objectmethod.ecommerce.service.dto.UserDTO;

@Service
public class JWTService {

	@Autowired
	UserMapper userMapper;

	private static final String MY_SECRET_JWT_KEY = "Famme-vede-er-carrello";

	public String createJWTToken(UserDTO userDTO) {

		User user = userMapper.toEntity(userDTO);

		Calendar cal = Calendar.getInstance();

		int minute = cal.get(Calendar.MINUTE) + 60;
		if (minute > 60) {
			minute = minute - 60;
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
		}
		cal.set(Calendar.MINUTE, minute);

		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);
		String token = JWT.create().withClaim("user_id", user.getId()).withClaim("user_name", user.getUsername())
				.withExpiresAt(cal.getTime()).sign(alg);

		return token;
	}

	public boolean checkJWTToken(String jwtToken) {
		boolean valid = false;
		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);
		try {
			JWTVerifier ver = JWT.require(alg).build();
			DecodedJWT decoded = ver.verify(jwtToken);

			Long userId = decoded.getClaim("user_id").asLong();
			String userName = decoded.getClaim("user_name").asString();

			System.out.println("Utente verificato! " + userId + " - " + userName);
			valid = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return valid;
	}

	public UserDTO getUserDTOByToken(String jwtToken) {
		UserDTO userDTO = new UserDTO();
		Algorithm alg = Algorithm.HMAC256(MY_SECRET_JWT_KEY);
		try {
			JWTVerifier ver = JWT.require(alg).build();
			DecodedJWT decoded = ver.verify(jwtToken);

			Long userId = decoded.getClaim("user_id").asLong();
			String username = decoded.getClaim("user_name").asString();

			userDTO.setId(userId);
			userDTO.setUsername(username);

			System.out.println("Utente verificato! " + userId + " - " + username);

		} catch (Exception e) {
			e.printStackTrace();
			userDTO = null;
		}
		return userDTO;
	}
}