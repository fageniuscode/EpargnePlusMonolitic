package com.fageniuscode.epargneplus.api.filters;

import com.fageniuscode.epargneplus.api.uitilities.JWTTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.fageniuscode.epargneplus.api.constants.SecurityConstant.OPTIONS_HTTP_METHOD;
import static com.fageniuscode.epargneplus.api.constants.SecurityConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JWTTokenProvider jwtTokenProvider;
    public JwtAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    /***
     * implémentation d'un filtre de sécurité pour une application web en utilisant JSON Web Token (JWT) pour l'authentification.
     * La méthode "doFilterInternal" est une méthode protégée qui est invoquée lorsqu'une requête
     * HTTP est envoyée à l'application Web. Elle prend en paramètres l'objet HttpServletRequest,
     * l'objet HttpServletResponse et un objet FilterChain qui est utilisé pour exécuter les filtres suivants dans la chaîne.
     * Dans ce code, si la méthode de la requête est OPTIONS_HTTP_METHOD, le filtre renvoie simplement un statut OK.
     * Sinon, il vérifie si la requête contient une en-tête d'authentification valide en vérifiant si l'en-tête contient le préfixe TOKEN_PREFIX et en extrayant le jeton d'authentification.
     * Si le jeton est valide, le filtre crée une instance de l'objet Authentication à partir du jeton JWT et le stocke dans le contexte de sécurité (SecurityContextHolder)
     * afin de protéger les ressources de l'application Web.
     * Sinon, le contexte de sécurité est effacé pour éviter toute attaque de sécurité.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token);
            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}