package com.assignment.api.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.api.constants.CodeMessage;
import com.assignment.api.model.APIResponse;
import com.assignment.api.security.util.JwtUtil;
import com.assignment.api.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
    JwtUtil jwtUtils;

    @Autowired
    UserService userDetailsService;
    
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwt = requestTokenHeader.substring(7);
            try{
                username = jwtUtils.getUsernameFromToken(jwt);
            }catch(ExpiredJwtException e){
            	 APIResponse response = new APIResponse();
            	 response.setCode(CodeMessage.TOKEN_EXPIRED);
            	 response.setMessage(CodeMessage.TOKEN_EXPIRED_MESSAGE);
            	 response.setPayload("");

                 httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                 httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

                 mapper.writeValue(httpServletResponse.getWriter(), response);
            }
        }

        // Once we get the token validate it.
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            if(jwtUtils.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtUtils.getAuhentication(jwt,SecurityContextHolder.getContext().getAuthentication(),userDetails);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
