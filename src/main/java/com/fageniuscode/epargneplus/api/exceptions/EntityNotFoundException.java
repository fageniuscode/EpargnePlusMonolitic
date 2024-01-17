package com.fageniuscode.epargneplus.api.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Ibrahima
 *	Permet de gérer de données liées à notre bd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    String message;
}
