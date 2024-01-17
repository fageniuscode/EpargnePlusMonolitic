package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.commons.RegisterRequest;
import com.fageniuscode.epargneplus.api.domains.UserPrincipal;
import com.fageniuscode.epargneplus.api.entities.dto.UserDTO;
import com.fageniuscode.epargneplus.api.entities.Role;
import com.fageniuscode.epargneplus.api.entities.User;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.exceptions.domain.EmailExistException;
import com.fageniuscode.epargneplus.api.exceptions.domain.EmailNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.domain.UserNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.domain.UsernameExistException;
import com.fageniuscode.epargneplus.api.mappings.UserMapper;
import com.fageniuscode.epargneplus.api.repositories.UserRepository;
import com.fageniuscode.epargneplus.api.services.EmailService;
import com.fageniuscode.epargneplus.api.services.LoginAttemptService;
import com.fageniuscode.epargneplus.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.fageniuscode.epargneplus.api.constants.UserImplConstant.*;

@Service
@Transactional
@RequiredArgsConstructor
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    private BCryptPasswordEncoder passwordEncoder;

    private LoginAttemptService loginAttemptService;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private EmailService emailService;

    //@Value("${microservice.account-service.endpoints.endpoint.uri}")
    //private String ENDPOINT_URL;
    LocalDateTime now = LocalDateTime.now();

    @Override
    public RegisterRequest createRegisterRequest(RegisterRequest userRegisterRequest) {

        return null;
    }

    /*@Transactional(readOnly = true)
    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(userMapper::userToUserDTO);
    }*/

    @Override
    public UserDTO register(String firstName, String lastName, String email, String username, LocalDate dateNaiss, String nationality, String gender, String phoneNumber) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        return null;
    }

    @Override
    public UserDTO addNewUser(String firstName, String lastName, String email, String username, LocalDate dateNaiss, String nationality, String gender, String phoneNumber, List<Role> rolesEntities, boolean isActive, boolean isNotLocked) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        List<UserDTO> userDTOList = userRepository.findAll(pageable)
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(userDTOList, pageable, userDTOList.size());
    }


    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("user.notfound", userId)));
        return userMapper.userToUserDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userMapper.userToUserDTO(user);
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        if (userRepository.existsById(user.getId())) {
            throw new RequestException(getMessage("user.exists", user.getId()), HttpStatus.BAD_REQUEST);
        }
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    private String generateWalletNumber() {
        // Vous pouvez implémenter la logique pour générer un numéro de portefeuille unique ici.
        // Par exemple, utilisez un générateur de numéros aléatoires ou une autre méthode appropriée.
        return "1234567890"; // Exemple de numéro de portefeuille factice.
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO){
        return userRepository.findById(userId)
                .map(entity -> {
                    userDTO.setId(userId);
                    return userMapper.userToUserDTO(userRepository.save(userMapper.userDTOToUser(userDTO)));
                }).orElseThrow(() -> new EntityNotFoundException(getMessage("user.notfound", userId)));
    }

    @Override
    public UserDTO updatedUser(String firstName, String lastName, String email, String username, LocalDate dateNaiss, String nationality, String gender, String phoneNumber, List<Role> rolesEntities, boolean isActive, boolean isNotLocked) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        return null;
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RequestException(getMessage("user.errordeletion", new Object[]{userId},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }

    @Override
    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        // User user = userRepository.findUserByEmail(email);
        UserDTO userDTO = findUserByEmail(email);
        if (userDTO == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        userDTO.setPassword(encodePassword(password));
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
    }

    @Override
    public Page<UserDTO> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDTO> findByDateNaissAfter(LocalDateTime dateTime, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDTO> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDTO> findByDateNaissBefore(LocalDateTime dateTime, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDTO> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDTO> findAllByDateNaissBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return null;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return userMapper.userToUserDTO(user);
    }

    // Méthode pour obtenir le message d'erreur depuis le fichier de propriétés
    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userMapper.userToUserDTO(userRepository.findUserByUsername(username));
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            User userSaved = userMapper.userDTOToUser(user);
            userRepository.save(userSaved);
            UserPrincipal userPrincipal = new UserPrincipal(userSaved);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    private String getRoleEnumName(Role role) {
        return role.getName().toUpperCase();
    }

    private void validateLoginAttempt(UserDTO user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private UserDTO validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        //User userByNewUsername = findUserByUsername(newUsername);
        UserDTO userByNewUsername = userMapper.userToUserDTO(userRepository.findUserByUsername(newUsername));
        UserDTO userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            UserDTO currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
