package com.assignment.api.security.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	//public static final long JWT_TOKEN_VALIDITY = 60 * 60;

    private String secret = "test";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    /*public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }*/

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    /*private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }*/

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        //Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(userDetails, userDetails.getUsername());
    }

    private String doGenerateToken(UserDetails claims, String subject) {
    	Map<String, Object> cclaims = new HashMap<>();
        final String authorities = claims.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        cclaims.put("AUTHORITIES_KEY", authorities);
        cclaims.put("NAME", claims.getUsername());
        return Jwts.builder()
                .setClaims(cclaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    public UsernamePasswordAuthenticationToken getAuhentication(final String token, final Authentication existingAuthentication, final UserDetails userDetails){
        final JwtParser jwtParser = Jwts.parser().setSigningKey(secret);
        final Jws claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = (Claims)claimsJws.getBody();

        final Collection authorities =
                Arrays.stream(claims.get("AUTHORITIES_KEY").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
