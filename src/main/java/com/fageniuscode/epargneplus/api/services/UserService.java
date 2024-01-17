package com.fageniuscode.epargneplus.api.services;
import com.fageniuscode.epargneplus.api.commons.RegisterRequest;
import com.fageniuscode.epargneplus.api.entities.dto.UserDTO;
import com.fageniuscode.epargneplus.api.entities.Role;
import com.fageniuscode.epargneplus.api.exceptions.domain.EmailExistException;
import com.fageniuscode.epargneplus.api.exceptions.domain.EmailNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.domain.UserNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.domain.UsernameExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface UserService {
    UserDTO register(String firstName, String lastName, String email, String username,
                     LocalDate dateNaiss, String nationality, String gender, String phoneNumber) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;
    UserDTO addNewUser(String firstName, String lastName, String email, String username,
                       LocalDate dateNaiss, String nationality, String gender, String phoneNumber,
                       List<Role> rolesEntities, boolean isActive, boolean isNotLocked) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
    // Liste tous les utilisateurs
    Page<UserDTO> getAllUsers(Pageable pageable);
    // Récupère un utilisateur par son ID
    UserDTO getUserById(Long userId);
    // Récupère un utilisateur par son Email
    UserDTO findUserByEmail(String email);
    // Récupère un utilisateur par son username
    UserDTO findUserByUsername(String username);
    // Crée un nouvel utilisateur
    UserDTO createUser(UserDTO userDTO);
    RegisterRequest createRegisterRequest(RegisterRequest userRegisterRequest);
    // Met à jour les informations d'un utilisateur
    UserDTO updateUser(Long userId, UserDTO userDTO);
    UserDTO updatedUser(String firstName, String lastName, String email, String username,
                       LocalDate dateNaiss, String nationality, String gender, String phoneNumber,
                       List<Role> rolesEntities, boolean isActive, boolean isNotLocked) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
    // Supprime un utilisateur par son ID
    void deleteUser(Long userId) throws IOException;
    void resetPassword(String email) throws MessagingException, EmailNotFoundException;
    Page<UserDTO> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable);
    Page<UserDTO> findByDateNaissAfter(LocalDateTime dateTime, Pageable pageable);
    Page<UserDTO> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable);
    Page<UserDTO> findByDateNaissBefore(LocalDateTime dateTime, Pageable pageable);
    Page<UserDTO> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
    Page<UserDTO> findAllByDateNaissBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

}

