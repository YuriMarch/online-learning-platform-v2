package com.distancelearning.authuser.configs.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Log4j2
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtString = getTokenHeader(request);
            if (jwtString != null && jwtProvider.validateJwt(jwtString)){
                String userId = jwtProvider.getSubjectJwt(jwtString);
                UserDetails userDetails = userDetailsService.loadUserByUserId(UUID.fromString(userId));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot set User Authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenHeader(HttpServletRequest request) {
            String headerAuth = request.getHeader("Authorization");
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
                return headerAuth.substring(7);
            }
            return null;
    }
}
