package com.fageniuscode.epargneplus.api.domains;

import com.fageniuscode.epargneplus.api.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import static java.util.Arrays.stream;
import java.util.Collection;
import java.util.stream.Collectors;

/***
 * @org.springframework.security.core.userdetails.UserDetails. Cette classe est utilisée pour fournir des informations sur un utilisateur à Spring Security lors de l'authentification.
 */
public class UserPrincipal implements UserDetails {
    private User user;
    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * getAuthorities(): retourne une collection d'objets GrantedAuthority qui représentent les
     * autorisations de l'utilisateur. Ces autorisations sont extraites de la propriété authorities de
     * l'objet User et converties en objets SimpleGrantedAuthority.
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(this.user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    /**
     * getPassword() retourne le mot de passe de l'utilisateur, qui est stocké dans la
     * propriété password de l'objet User
     * @return
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }
    /**
     * getUsername() retourne le nom d'utilisateur de l'utilisateur, qui est stocké dans la
     * propriété username de l'objet User.
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * Les méthodes isAccountNonExpired(), isCredentialsNonExpired() et isEnabled()
     * renvoient toutes des booléens qui indiquent si le compte de l'utilisateur est actif et non expiré,
     * si les informations d'identification de l'utilisateur ne sont pas expirées et si le compte de l'utilisateur est activé.
     * Ces informations sont stockées dans les propriétés accountNonExpired, credentialsNonExpired et active respectivement de l'objet User.
     * La méthode isAccountNonLocked() renvoie également un booléen qui indique si le compte de l'utilisateur n'est pas verrouillé, cette information étant stockée dans la propriété notLocked de l'objet User.
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }


}
