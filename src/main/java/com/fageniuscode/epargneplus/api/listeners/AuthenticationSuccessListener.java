package com.fageniuscode.epargneplus.api.listeners;
import com.fageniuscode.epargneplus.api.domains.UserPrincipal;
import com.fageniuscode.epargneplus.api.services.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
@Component
public class AuthenticationSuccessListener {
    private LoginAttemptService loginAttemptService;
    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }
    /***
     * écouteur d'événements pour gérer les succès d'authentification dans une application.
     * Il utilise l'annotation @EventListener pour spécifier que la méthode doit être appelée lorsqu'un
     * événement AuthenticationSuccessEvent est déclenché.
     * Dans la méthode onAuthenticationSuccess, la première ligne récupère l'objet principal à partir de
     * l'événement AuthenticationSuccessEvent. Le principal est l'entité qui est authentifiée (par exemple, un utilisateur).
     * Ensuite, la méthode vérifie si le principal est une instance de la classe UserPrincipal.
     * Si c'est le cas, cela signifie qu'un utilisateur s'est connecté avec succès, donc la méthode
     * convertit le principal en un objet UserPrincipal.
     * @param event
     */
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
