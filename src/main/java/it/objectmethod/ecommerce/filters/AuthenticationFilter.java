package it.objectmethod.ecommerce.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.service.JWTService;

@Component
@Order(3)
public class AuthenticationFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Autowired
	private JWTService jwtSrv;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logger.info("INIZIO FILTRO");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		String url = httpReq.getRequestURI();
		logger.info("STO CONTROLLANDO " + url);

		if (url.endsWith("login")) {
			logger.info("RICHIESTA APPROVATA");
			chain.doFilter(request, response);
		} else {
			String token = httpReq.getHeader("auth-token");
			if (token != null) {
				if (jwtSrv.checkJWTToken(token)) {
					logger.info("TOKEN VALIDO RICHIESTA APPROVATA");
					chain.doFilter(request, response);
				} else {
					logger.warn("TOKEN NON VALIDO RICHIESTA BLOCCATA");
					httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}
			} else {
				logger.error("TOKEN NON PRESENTE RICHIESTA BLOCCATA");
				httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		logger.info("FINE FILTRO");
	}
}