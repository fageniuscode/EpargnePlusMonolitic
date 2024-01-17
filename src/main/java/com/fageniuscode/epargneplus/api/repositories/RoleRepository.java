package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
