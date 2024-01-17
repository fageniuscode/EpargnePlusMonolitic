package com.fageniuscode.epargneplus.api.configs;

import com.fageniuscode.epargneplus.api.filters.JwtAccessDeniedHandler;
import com.fageniuscode.epargneplus.api.filters.JwtAuthenticationEntryPoint;
import com.fageniuscode.epargneplus.api.filters.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.fageniuscode.epargneplus.api.constants.SecurityConstant.PUBLIC_URLS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JwtAuthorizationFilter jwtAuthorizationFilter;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /***
     * Ce constructeur est utilisé pour injecter les dépendances nécessaires à la configuration de la sécurité dans une application Spring.
     * @param jwtAuthorizationFilter : un filtre personnalisé qui vérifie la présence et la validité d'un jeton d'authentification JWT (JSON Web Token) pour chaque requête entrante.
     * @param jwtAccessDeniedHandler : une classe qui gère les erreurs de refus d'accès.
     * @param jwtAuthenticationEntryPoint : une classe qui gère les erreurs d'authentification.
     * @param userDetailsService : une interface Spring Security qui est utilisée pour charger les informations de l'utilisateur à partir de la source de données.
     * @param bCryptPasswordEncoder : une classe de codage de mot de passe qui est utilisée pour chiffrer les mots de passe des utilisateurs dans la source de données.
     */
    @Autowired
    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                 JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("userDetailsService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /***
     * Cette méthode configure l'authentification pour une application Spring Security en utilisant un objet AuthenticationManagerBuilder
     * @userDetailsService : Va permettre de charger les détails d'un utilisateur (par exemple, son nom d'utilisateur et son mot de passe) depuis une source de données, telle qu'une base de données, pour effectuer l'authentification.
     * @bCryptPasswordEncoder : Est utilisé pour encoder le mot de passe de l'utilisateur afin de le stocker de manière sécurisée dans la source de données. L'algorithme de hachage bcrypt est souvent utilisé pour cela car il est considéré comme l'un des algorithmes les plus sécurisés pour le hachage de mots de passe.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /****
     * Configuration des paramètres de sécurité
     * @http.csrf().disable().cors().and() :  Cela désactive la protection CSRF et active CORS.
     * @.sessionManagement().sessionCreationPolicy(STATELESS) : Cette configuration permet de créer une session.
     * Ici, STATELESS signifie que le serveur ne maintient pas l'état de session pour l'application. Chaque demande est autonome et ne dépend pas des demandes précédentes.
     * @.and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll() : Cela configure les autorisations pour les URL publiques'
     * PUBLIC_URLS est une variable qui contient les URL qui sont accessibles sans authentification. Ici, permitAll() autorise toutes les demandes à ces URL.
     * @.anyRequest().authenticated() : Cela spécifie que toutes les autres demandes nécessitent une authentification pour être traitées.
     * @.and().exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(jwtAuthenticationEntryPoint) : Cela configure les gestionnaires d'exceptions pour la gestion des erreurs lorsqu'un utilisateur non autorisé essaie d'accéder à une ressource protégée.
     * @.and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) : Cela configure le filtre d'authentification Cela ajoute un filtre (jwtAuthorizationFilter) avant le filtre d'authentification par nom d'utilisateur et mot de passe (UsernamePasswordAuthenticationFilter). Le filtre d'autorisation JWT vérifie si la demande contient un jeton d'authentification valide..
     * @jwtAuthorizationFilter : Cette configuration permet de créer un filtre d'authentification.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /***
     *  Cette méthode est utilisée pour configurer et fournir une instance d'AuthenticationManager à l'application Spring.
     * @AuthenticationManager : Est une interface dans Spring Security qui fournit des méthodes pour authentifier les utilisateurs.
     * @authenticationManagerBean() : permet à l'application Spring de récupérer une instance d'AuthenticationManager à partir du contexte de l'application.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
