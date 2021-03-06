package net.juanxxiii.womb.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//Manage user's authentication
public class AuthenticationRequest extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public AuthenticationRequest(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/syslogin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            net.juanxxiii.womb.database.entities.UserLoginSystem creds = new ObjectMapper().readValue(request.getInputStream(), net.juanxxiii.womb.database.entities.UserLoginSystem.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Could not request " + e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        String token = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers","Authorization");
    }
}
