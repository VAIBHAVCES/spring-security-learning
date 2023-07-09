package com.example.amigossecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor // if I create a new variable in class it will automatically create consturctor for it

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
                                ) throws ServletException, IOException {

        //request => its for intercepting sent by user
        // response => for adding something in response
        // filterChain => list of other filter which we need to execute, so on calling filter.Do next filter will executed
        final String header = request.getHeader("Authorization");
        log.info("This is doFilterInternal method from jwt filter with payload : {}",request);
        final String jwt;
        if( header==null  || !header.startsWith("Bearer")){
            log.info("Filters are not populated correctly hence returning it");
            filterChain.doFilter(request,response); // pass req tp next filter
            return;
        }
        log.info("Coming at this point means at least headers have some token");
        jwt= header.substring(7);
        String username = jwtService.extractUsername(jwt);
        // this stores that user is authenticated or not yet :  SecurityContextHolder.getContext().getAuthentication()
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.isValidToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Setted the authentication in the security context hence now see the auth status: {} lets see if its valid : {}", SecurityContextHolder.getContext().getAuthentication(),SecurityContextHolder.getContext().getAuthentication().isAuthenticated() );

            }

        }
        filterChain.doFilter(request,response);



    }
}
