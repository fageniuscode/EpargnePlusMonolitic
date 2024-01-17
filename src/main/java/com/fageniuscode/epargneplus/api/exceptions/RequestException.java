package com.fageniuscode.epargneplus.api.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
/**
 * @author Ibrahima
 *	Permet de gérer les erreurs de requête
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestException extends RuntimeException {
    String message;
    HttpStatus status;
}
