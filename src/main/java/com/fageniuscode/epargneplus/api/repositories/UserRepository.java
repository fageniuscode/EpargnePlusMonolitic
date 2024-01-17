package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    Optional<User> findById(Long id);
    Page<User> findByLastName(String lastName, Pageable pageable);
    Page<User> findByFirstName(boolean firstName, Pageable pageable);
    Page<User> findByDateNaiss(LocalDate dateNaiss, Pageable pageable);
    Page<User> findByNationality(String nationality, Pageable pageable);
    Page<User> findByGender(String gender, Pageable pageable);
    User findUserByUsername(String username);
    Page<User> findByCreatedDateAfter(LocalDateTime dateTime, Pageable pageable);
    Page<User> findByDateNaissAfter(LocalDateTime dateTime, Pageable pageable);
    Page<User> findByCreatedDateBefore(LocalDateTime dateTime, Pageable pageable);
    Page<User> findByDateNaissBefore(LocalDateTime dateTime, Pageable pageable);
    Page<User> findAllByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
    Page<User> findAllByDateNaissBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

}
