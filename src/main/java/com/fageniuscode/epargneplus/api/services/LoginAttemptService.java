package com.fageniuscode.epargneplus.api.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class LoginAttemptService {
    /**
     * définit une classe qui utilise la bibliothèque de cache de Google Guava pour stocker le nombre de tentatives de connexion échouées pour chaque utilisateur. Plus précisément, le code crée un objet LoadingCache qui utilise une clé de chaîne (String) pour stocker un nombre entier (Integer).
     * @MAXIMUM_NUMBER_OF_ATTEMPTS : qui définit le nombre maximum de tentatives de connexion échouées avant que l'utilisateur ne soit bloqué.
     * @ATTEMPT_INCREMENT : qui définit le nombre d'essais qui doivent être incrémentés à chaque fois qu'une tentative de connexion échoue.
     */
    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String, Integer> loginAttemptCache;

    /**
     * définit un service de gestion de tentatives de connexion.
     * Le service utilise la bibliothèque CacheBuilder de Google Guava pour créer un objet Cache, qui stocke le nombre de tentatives de connexion échouées pour chaque utilisateur.
     * Le cache est défini avec une durée de validité de 15 minutes et une taille maximale de 100 éléments.
     * Lorsqu'un utilisateur tente de se connecter, le nombre de tentatives de
     * connexion échouées est récupéré à partir du cache pour cet utilisateur. Si le nombre de tentatives
     * de connexion échouées dépasse un seuil défini,
     * le compte de l'utilisateur peut être verrouillé ou bloqué pendant un certain temps.
     * La méthode load du CacheLoader fournit une valeur par défaut (0) pour les utilisateurs qui n'ont pas encore de tentative de connexion échouée stockée dans le cache.
     */
    public LoginAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    /***
     * prend un paramètre de chaîne (String) "username". Cette méthode est probablement définie dans une classe qui gère le cache des tentatives de connexion des utilisateurs.
     * La méthode utilise la bibliothèque Google Guava pour invalider (ou supprimer) l'entrée de cache associée à la clé "username".
     * Plus précisément, la méthode appelle la méthode "invalidate" du cache Guava avec la clé "username"
     * en tant qu'argument, ce qui provoque la suppression de l'entrée de cache correspondante.
     * @param username
     */
    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
    }

    /***
     * Ajoute un utilisateur à un cache de tentatives de connexion. Plus précisément, il incrémente le nombre de tentatives de connexion pour l'utilisateur donné en entrée. Si l'utilisateur
     * n'est pas déjà dans le cache, il est ajouté avec une tentative de connexion initiale de 1.
     * Plus précisément, le code récupère d'abord le nombre de tentatives de connexion actuelles pour l'utilisateur donné en entrée à partir du cache loginAttemptCache. Si l'utilisateur n'est pas dans le cache, une exception ExecutionException est
     * levée et la variable attempts est initialisée à ATTEMPT_INCREMENT.
     * Si l'utilisateur est déjà dans le cache, le nombre de tentatives de connexion est incrémenté de ATTEMPT_INCREMENT.
     * Ensuite, le code met à jour le cache avec le nouveau nombre de tentatives de connexion pour l'utilisateur. Notez que le cache doit être défini ailleurs dans le code et que le type de cache utilisé est inconnu.
     * @param username
     */
    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(username, attempts);
    }

    /***
     * vérifie si un utilisateur a dépassé le nombre maximum de tentatives de connexion.
     * La méthode prend un nom d'utilisateur en entrée et utilise un cache de tentative de connexion
     * (loginAttemptCache) pour récupérer le nombre de tentatives de connexion échouées pour cet utilisateur. Si le nombre de tentatives de connexion échouées dépasse ou est égal au nombre maximal de tentatives (MAXIMUM_NUMBER_OF_ATTEMPTS),
     * la méthode renvoie true. Sinon, elle renvoie false.
     * Si une exception ExecutionException est levée lors de la récupération des données de cache, la méthode affiche la trace de la pile (stack trace) de l'exception et renvoie false.
     * @param username
     * @return
     */
    public boolean hasExceededMaxAttempts(String username) {
        try {
            return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
