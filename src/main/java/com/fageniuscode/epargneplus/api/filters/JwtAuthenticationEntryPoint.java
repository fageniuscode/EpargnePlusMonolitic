package com.fageniuscode.epargneplus.api.filters;

import com.fageniuscode.epargneplus.api.domains.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.fageniuscode.epargneplus.api.constants.SecurityConstant.FORBIDDEN_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    /***
     * Cette méthode est appelée par le filtre d'authentification (AuthenticationFilter) lorsqu'une tentative d'authentification échoue pour une demande (request) protégée par une authentification de type "form-login" ou "basic-auth".
     * Plus précisément, cette méthode envoie une réponse d'erreur HTTP 403 Forbidden (Interdit)
     * avec un message d'erreur en JSON. Elle crée une instance de la classe HttpResponse qui contient
     * le code HTTP, le message, la raison et le message d'erreur personnalisé. Elle configure ensuite
     * la réponse HTTP avec le type de contenu JSON et le code HTTP 403. Elle écrit ensuite la réponse HTTP JSON dans le flux de sortie (output stream) de la réponse HTTP. Enfin, elle vide le flux de sortie en appelant la méthode "flush".
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        HttpResponse httpResponse = new HttpResponse(FORBIDDEN.value(), FORBIDDEN, FORBIDDEN.getReasonPhrase().toUpperCase(), FORBIDDEN_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
