package com.fageniuscode.epargneplus.api.listeners;
import com.fageniuscode.epargneplus.api.services.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
@Component
public class AuthenticationFailureListener {
    private LoginAttemptService loginAttemptService;
    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    /***
     * méthode d'écouteur d'événements qui sera appelée lorsque l'événement AuthenticationFailureBadCredentialsEvent sera déclenché. L'événement se produit lorsque l'authentification
     * d'un utilisateur échoue en raison de mauvaises informations d'identification.
     * La méthode commence par extraire l'objet principal de l'événement d'authentification,
     * qui contient des informations sur l'utilisateur qui a tenté de se connecter.
     * Si l'objet principal est une chaîne de caractères, la méthode extrait
     * le nom d'utilisateur de l'authentification et l'ajoute à un cache de tentatives de connexion.
     * @param event
     */
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(username);
        }

    }

}
