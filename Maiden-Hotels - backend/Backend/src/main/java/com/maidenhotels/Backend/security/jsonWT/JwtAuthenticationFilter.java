package com.maidenhotels.Backend.security.jsonWT;

import com.maidenhotels.Backend.security.models.BackofficeDetailsModel;
import com.maidenhotels.Backend.security.models.ClientDetailsModel;
import com.maidenhotels.Backend.security.models.LoginViewModel;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private static final String ORIGIN = "Origin";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /* Trigger when we issue POST request to /login
    We also need to pass in {"username":"admin", "password":"admin"} in the request body
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //Important to solve CORS problem
        if (request.getHeader(ORIGIN) != null) {
            String origin = request.getHeader(ORIGIN);
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        }

        // Grab credentials and map them to login viewmodel
        LoginViewModel credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Grab user
        UserDetails user = (UserDetails) authResult.getPrincipal();
        String username = user.getUsername();
        if(username.contains("@")){
            user = (ClientDetailsModel) authResult.getPrincipal();
        }else{
            user = (BackofficeDetailsModel) authResult.getPrincipal();
        }
        // Create JWT Token

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + com.maidenhotels.Backend.security.jsonWT.JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(com.maidenhotels.Backend.security.jsonWT.JwtProperties.SECRET.getBytes()));

        // Add token in response

        response.addHeader(com.maidenhotels.Backend.security.jsonWT.JwtProperties.HEADER_STRING, com.maidenhotels.Backend.security.jsonWT.JwtProperties.TOKEN_PREFIX + token);

        String finalAnswer = new Gson().toJson(com.maidenhotels.Backend.security.jsonWT.JwtProperties.TOKEN_PREFIX + token);
        // Add token and body to the response
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(finalAnswer);
        out.flush();
    }


}
