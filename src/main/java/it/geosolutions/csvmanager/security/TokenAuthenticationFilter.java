package it.geosolutions.csvmanager.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter  {

	public TokenAuthenticationFilter(AuthenticationManager authMan) {
		super(authMan);
	}
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = Optional.ofNullable(req.getHeader("smb"))
                .orElseThrow( () -> new BadCredentialsException("Missing authentication token."));

        Authentication auth= getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(token, token));
       if(auth!=null) {
    	   SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(req, res);
       }
	}

}
