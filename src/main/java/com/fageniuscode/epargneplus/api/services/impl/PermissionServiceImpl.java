package com.fageniuscode.epargneplus.api.services.impl;
import com.fageniuscode.epargneplus.api.entities.dto.PermissionDTO;
import com.fageniuscode.epargneplus.api.entities.Permission;
import com.fageniuscode.epargneplus.api.enumeration.PermissionType;
import com.fageniuscode.epargneplus.api.enumeration.SecurityAction;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.mappings.PermissionMapper;
import com.fageniuscode.epargneplus.api.repositories.PermissionRepository;
import com.fageniuscode.epargneplus.api.services.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final MessageSource messageSource;

    @Override
    public PermissionDTO savePermission(PermissionDTO permissionDTO) {
        PermissionDTO returnPermission = null;

        permissionDTO.setId((long) 0);
        String permissionName = permissionDTO.getEntityName() + "-" + permissionDTO.getPermissionType().toString() + "-" + ((permissionDTO.getSecurityAction() != null) ? permissionDTO.getSecurityAction().toString() : "");
        permissionDTO.setName(permissionName.toLowerCase());
        Permission permission = permissionMapper.permissionDTOToPermission(permissionDTO);
        permissionRepository.save(permission);

        return returnPermission;
    }

    @Override
    public Optional<PermissionDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<PermissionDTO> findAllWithPagination(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAllPermissions() {

    }

    @Override
    public void deletePermissionById(Long id) {

    }

    @Override
    public PermissionDTO updatePermission(Long id, PermissionDTO updatedPermissionDTO){
        Permission returnPermission;
        try {
            Optional<Permission> permissionById = permissionRepository.findById(id);
            if (!permissionById.isPresent()) {
                throw new EntityNotFoundException(getMessage("auditlog.notfound", id));
            }
            String permissionName = updatedPermissionDTO.getEntityName() + "-" + ((updatedPermissionDTO.getPermissionType() != null) ? updatedPermissionDTO.getPermissionType().toString() : PermissionType.ALLOW.toString()) + "-" + ((updatedPermissionDTO.getSecurityAction() != null) ? updatedPermissionDTO.getSecurityAction().toString() : "");
            updatedPermissionDTO.setName(permissionName.toLowerCase());
            updatedPermissionDTO.setId(id);

            Permission permission = permissionMapper.permissionDTOToPermission(updatedPermissionDTO);
            permissionRepository.save(permission);
            returnPermission = permissionRepository.save(permission);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Page<PermissionDTO> findAllByEntityNameAndSecurityAction(String entityName, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PermissionDTO> findAllByEntityNamePermissionTypeAndSecurityAction(String entityName, PermissionType permissionType, SecurityAction securityAction, Pageable pageable) {
        return null;
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
