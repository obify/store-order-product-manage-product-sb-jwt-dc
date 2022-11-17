package com.assignment.api.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter extends OncePerRequestFilter{

	 @Value("${cors.allow-origins}")
	    private String allowOrigins;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
	        List<String> corsAllowedOrigins = Arrays.asList(allowOrigins.split(","));
	        String origin = request.getHeader("Origin");

	        if(corsAllowedOrigins.contains(origin)){
	            response.setHeader("Access-Control-Allow-Origin", origin);
	        }

	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers",
	                "x-requested-with, authorization, Content-Type, Authorization,skip"); // , credential, X-XSRF-TOKEN
	        response.setHeader("Access-Control-Allow-Credentials", "true");

	        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            chain.doFilter(request, response);
	        }
	    }

	    @Override
	    public void destroy() {
	    }
}
