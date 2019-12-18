package it.geosolutions.csvmanager.security;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    
	@Autowired
	private Environment env;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Object principal = authentication.getPrincipal();
		if (principal !=null) {
			String token = String.valueOf(principal);
			if(token.equals(env.getProperty("secret"))){
				
				authentication = new UsernamePasswordAuthenticationToken(token, token,
						Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
			}
		} else {
			throw new BadCredentialsException("Invalid authentication token");
		}
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (Authentication.class.isAssignableFrom(authentication));
	}
	
	public boolean verifyToken (String token) {
		try {
		String key = env.getProperty("key");
		Key aesKey = new SecretKeySpec(key.getBytes(),"AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		String decrypted = new String(cipher.doFinal(token.getBytes()));
		if (decrypted.contentEquals("validString"))
		   return true;
		} catch(BadPaddingException bpe) {
			return false;
		} catch (Exception e) {
			throw new RuntimeException (e);
		}
		return false;
	}

	

}
