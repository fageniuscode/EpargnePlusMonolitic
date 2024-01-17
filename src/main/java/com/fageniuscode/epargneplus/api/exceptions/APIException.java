package com.fageniuscode.epargneplus.api.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
/**
 * @author Ibrahima
 *	{@link #message}  de retourner un message très claire à chaque fois
 *  qu'on fait appel à un EndPoint et qu'il y ait un problème
 *  {@link #status} retourne le statut de l'erreur
 *  {@link #timestamp} retourne la date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIException {
    String message;
    HttpStatus status;
    LocalDateTime timestamp;
}
