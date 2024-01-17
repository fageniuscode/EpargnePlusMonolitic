package com.fageniuscode.epargneplus.api.services;

import com.fageniuscode.epargneplus.api.entities.dto.PermissionDTO;
import com.fageniuscode.epargneplus.api.enumeration.PermissionType;
import com.fageniuscode.epargneplus.api.enumeration.SecurityAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PermissionService {

    PermissionDTO savePermission(PermissionDTO permissionDTO);

    Optional<PermissionDTO> findById(Long id);

    Page<PermissionDTO> findAllWithPagination(Pageable pageable);

    void deleteAllPermissions();

    void deletePermissionById(Long id);

    PermissionDTO updatePermission(Long id, PermissionDTO updatedPermissionDTO);

    Page<PermissionDTO> findAllByEntityNameAndSecurityAction(String entityName, Pageable pageable);

    Page<PermissionDTO> findAllByEntityNamePermissionTypeAndSecurityAction(String entityName, PermissionType permissionType, SecurityAction securityAction, Pageable pageable);

}
