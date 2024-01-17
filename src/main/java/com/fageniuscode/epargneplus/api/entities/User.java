package com.fageniuscode.epargneplus.api.entities;

import com.fageniuscode.epargneplus.api.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Entity
@Data
@Table(name = "ep_user")
@Getter
@Setter
@NoArgsConstructor
@JsonFilter("com.fageniuscode.as.api.User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;
    @Column(nullable = false, length = 150)
    private String lastName;
    @Column(nullable = false, length = 200)
    private String firstName;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false)
    private LocalDate dateNaiss;
    @Column(nullable = false, length = 150)
    private String nationality;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false, length = 40)
    private String username;
    @Column(nullable = false, length = 20)
    private String phoneNumber;
    @Column(nullable = false)
    private LocalDateTime lastLoginDate;
    @Column(nullable = false)
    private LocalDateTime lastLoginDateDisplay;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "ep_user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> rolesEntities;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;

    public User(Long id, String lastName, String firstName, String email, String password, LocalDate dateNaiss, String nationality, Gender gender, String username, String phoneNumber, LocalDateTime lastLoginDate, LocalDateTime lastLoginDateDisplay, LocalDateTime createdDate, boolean isActive, boolean isNotLocked) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.dateNaiss = dateNaiss;
        this.nationality = nationality;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.createdDate = createdDate;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public User(Long id, String lastName, String firstName, String email, String password, LocalDate dateNaiss, String nationality, Gender gender, String username, String phoneNumber, LocalDateTime lastLoginDate, LocalDateTime lastLoginDateDisplay, LocalDateTime createdDate, List<Role> rolesEntities, String[] authorities, boolean isActive, boolean isNotLocked) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.dateNaiss = dateNaiss;
        this.nationality = nationality;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.createdDate = createdDate;
        this.rolesEntities = rolesEntities;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateNaiss=" + dateNaiss +
                ", nationality='" + nationality + '\'' +
                ", gender='" + gender + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", lastLoginDateDisplay=" + lastLoginDateDisplay +
                ", createdDate=" + createdDate +
                ", authorities=" + Arrays.toString(authorities) +
                ", isActive=" + isActive +
                ", isNotLocked=" + isNotLocked +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        User rhs = (User) obj;
        return new EqualsBuilder()
                .append(username, rhs.username)
                .build();
    }
}

