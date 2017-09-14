package com.example.demobootsecurity.config;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.example.demobootsecurity.util.StringEncryptor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomPreAuthentocationFilter extends AbstractPreAuthenticatedProcessingFilter {
	
	//public static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		
		String username = request.getParameter("user");
		
		logger.info("********* getPreAuthenticatedCredentials - username: {}" + username);
		
		return null;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		
		String username = request.getParameter("user");
		
		logger.info("********* getPreAuthenticatedPrincipal - username: {}" + username);
		
		return null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.info(this + " ******** received authentication request from " + request.getRemoteHost() + " to " + request.getLocalName());
		
		if (request instanceof HttpServletRequest) {
	        if (isAuthenticationRequired()) {
	            try {
					// extract token from header
					//OEWebToken token = extractToken(request);

					// dump token into security context (for authentication-provider to pick up)
					//SecurityContextHolder.getContext().setAuthentication(token);
					
					String d = ((HttpServletRequest) request).getParameter("d");
					String v = ((HttpServletRequest) request).getParameter("v");
					
					logger.info("***** Param d=" + d + " - Param v=" + v);
					
					Object decryptObject = StringEncryptor.decryptObject(d, v);
					
					ObjectMapper om = new ObjectMapper();
					om.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
					om.configure(Feature.ALLOW_SINGLE_QUOTES, true);
					//om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
					ParamsDto paramsDto = om.readValue(decryptObject.toString().replaceAll("=", ":"), ParamsDto.class);
					
					logger.info("***** Decrypted Params: " + paramsDto.toString());
					
					MmsUserDetails mmsUser = new MmsUserDetails(paramsDto.getLogin(), "ADMIN");
					//new UsernamePass
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(mmsUser, mmsUser.getPassword(), mmsUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        } else {
	            logger.info("session already contained valid Authentication - not checking again");
	        }
	    }
		
		chain.doFilter(request, response);
	}
	
	private boolean isAuthenticationRequired() {
	    // apparently filters have to check this themselves.  So make sure they have a proper AuthenticatedAccount in their session.
	    Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
	    if ((existingAuth == null) || !existingAuth.isAuthenticated()) {
	        return true;
	    }

	    /*
	    if (!(existingAuth instanceof AuthenticatedAccount)) {
	        return true;
	    }
	    */

	    // current session already authenticated
	    return false;
	}

}
