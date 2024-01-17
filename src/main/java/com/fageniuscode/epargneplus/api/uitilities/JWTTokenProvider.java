package com.fageniuscode.epargneplus.api.uitilities;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fageniuscode.epargneplus.api.domains.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.fageniuscode.epargneplus.api.constants.SecurityConstant.*;
import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    /***
     * Cette méthode prend un objet UserPrincipal en tant que paramètre. UserPrincipal est généralement une classe qui implémente
     * l'interface UserDetails de Spring Security et représente les détails de l'utilisateur actuellement authentifié dans le système.
     * String[] claims = getClaimsFromUser(userPrincipal); : Appelle une méthode getClaimsFromUser(userPrincipal) pour obtenir les claims
     * (revendications) à inclure dans le token JWT. Les claims sont des informations associées à l'utilisateur, telles que les rôles,
     * les autorisations, etc
     * JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(GET_ARRAYS_ADMINISTRATION) :
     * Crée un nouveau builder pour la création d'un token JWT à l'aide de la bibliothèque JJWT.
     * Utilise withIssuer pour spécifier l'émetteur du token, dans ce cas, GET_ARRAYS_LLC.
     * Utilise withAudience pour spécifier l'audience du token, dans ce cas, GET_ARRAYS_ADMINISTRATION.
     * withIssuedAt(new Date()) : Utilise withIssuedAt pour spécifier la date d'émission du token, qui est définie à la date actuelle.
     * withSubject(userPrincipal.getUsername()) : Utilise withSubject pour spécifier le sujet du token, qui est généralement le nom d'utilisateur de l'utilisateur actuellement authentifié.
     * withArrayClaim(AUTHORITIES, claims) : Utilise withArrayClaim pour inclure un claim personnalisé dans le token. Dans ce cas, le claim est nommé AUTHORITIES et contient les rôles ou autorisations de l'utilisateur.
     * withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) : Utilise withExpiresAt pour spécifier la date d'expiration du token,
     * qui est définie à une date future calculée en ajoutant la constante EXPIRATION_TIME (probablement exprimée en millisecondes) à l'heure actuelle.
     * sign(HMAC512(secret.getBytes())) : Utilise sign pour signer le token avec un algorithme de hachage HMAC (HMAC SHA-512) en utilisant une clé secrète (secret).
     */
    public String generateJwtToken(UserPrincipal userPrincipal) {
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, claims).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));
    }

    /***
     * Cette méthode prend un jeton JWT en tant que paramètre.
     * String[] claims = getClaimsFromToken(token) : Elle appelle la méthode getClaimsFromToken(token) pour extraire les revendications (claims)
     * du jeton JWT. Les revendications dans un jeton JWT contiennent des informations telles que l'émetteur, le sujet, les autorités, etc.
     * return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList()): Elle prend le tableau de chaînes claims et le transforme en un flux (stream) de chaînes.
     * Pour chaque élément dans le flux, elle crée un objet SimpleGrantedAuthority en utilisant la chaîne comme argument
     * Elle collecte ensuite ces objets SimpleGrantedAuthority dans une liste.
     */
    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new
                UsernamePasswordAuthenticationToken(username, null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
        }catch (JWTVerificationException exception) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsFromUser(UserPrincipal user) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }
}
