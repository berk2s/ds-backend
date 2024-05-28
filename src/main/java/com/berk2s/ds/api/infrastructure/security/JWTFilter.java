package com.berk2s.ds.api.infrastructure.security;

import com.berk2s.ds.api.application.auth.TokenService;
import com.berk2s.ds.api.infrastructure.auth.controllers.AuthController;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final SecurityUserDetailsService securityUserDetailsService;

    private final List<String> excludedUrls = List.of(
            AuthController.ENDPOINT
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);

            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (StringUtils.isEmpty(headers) || !headers.startsWith("Bearer ")) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            final String accessToken = headers.split(" ")[1].trim();

            if (!tokenService.validate(accessToken)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            JWTClaimsSet jwtClaimsSet = tokenService.parseAndValidate(accessToken);

            Date date = jwtClaimsSet.getExpirationTime();
            Instant now = Instant.now();

            if (date.toInstant().isBefore(now)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String userId = jwtClaimsSet.getSubject();

            SecurityUser securityUser = (SecurityUser) securityUserDetailsService
                    .loadUserByUsername(userId);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(securityUser,
                            null, securityUser.getAuthorities());

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludedUrls.stream().anyMatch(skipUrl -> request.getRequestURI().startsWith(skipUrl));
    }
}
