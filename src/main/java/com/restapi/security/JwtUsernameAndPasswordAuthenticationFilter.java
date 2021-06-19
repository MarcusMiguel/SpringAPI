package com.restapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.models.UsernameAndPasswordAuthenticationRequest;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtConfig jwtConfig,
                                                      SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            Authentication authentication = null;

            UsernameAndPasswordAuthenticationRequest authenticationRequest =null;

            if(  request.getParameter("password") == null){
                authenticationRequest = new ObjectMapper()
                        .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
                authentication = new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getPassword(),
                        authenticationRequest.getUsername()
                );
            }
            else {
                authentication = new UsernamePasswordAuthenticationToken(
                        request.getParameter("password"),
                        request.getParameter("username"));
            }
            System.out.println(authentication.getCredentials());
        return authenticationManager.authenticate(authentication);
       } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(30).toInstant()))
                .signWith(secretKey)
                .compact();
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);

        super.successfulAuthentication(request, response, chain, authResult);
    }
}