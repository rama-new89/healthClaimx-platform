package com.healthclaimx.auth.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class ZoneValidationFilter extends OncePerRequestFilter {
    
    private static final String ZONE_HEADER = "X-Zone";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String zone = request.getHeader(ZONE_HEADER);
        
        if (zone != null && !zone.isEmpty()) {
            request.setAttribute("zone", zone);
        }
        
        filterChain.doFilter(request, response);
    }
}
