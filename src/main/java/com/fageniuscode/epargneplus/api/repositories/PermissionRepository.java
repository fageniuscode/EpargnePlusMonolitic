package com.fageniuscode.epargneplus.api.repositories;
import com.fageniuscode.epargneplus.api.entities.Permission;
import com.fageniuscode.epargneplus.api.enumeration.PermissionType;
import com.fageniuscode.epargneplus.api.enumeration.SecurityAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select a from Permission a order by a.name ASC")
    public Page<Permission> findAllWithPagination(Pageable pageable);
    @Query("select a from Permission a where LOWER(a.entityName)=LOWER(:entityName) and a.securityAction=:securityAction order by a.name ASC")
    public List<Permission> findAllByEntityNameAndSecurityAction(String entityName, SecurityAction securityAction);
    @Query("select a from Permission a where LOWER(a.entityName)=LOWER(:entityName) and a.permissionType=:permissionType and a.securityAction=:securityAction order by a.name ASC")
    public List<Permission> findAllByEntityNamePermissionTypeAndSecurityAction(String entityName, PermissionType permissionType, SecurityAction securityAction);

}