package com.securitysystem.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request)
            throws ServletException {

        String path = request.getServletPath();

        System.out.println("REQUEST PATH : " + path);

        return path.startsWith("/swagger-ui/")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/auth/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String tenantId =
                request.getHeader("X-Tenant-ID");

        System.out.println("Tenant Filter Executed");

        if (tenantId == null ||
                tenantId.isBlank()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter()
                    .write("Tenant ID Missing");

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}