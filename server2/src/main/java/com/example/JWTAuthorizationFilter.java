package com.example;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private FirebaseAuth firebaseAuth;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, FirebaseAuth firebaseAuth) {
        super(authenticationManager);
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            try {
                FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token.replace(TOKEN_PREFIX, ""));
                return new UsernamePasswordAuthenticationToken(firebaseToken.getName(), null, new ArrayList<>());
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
