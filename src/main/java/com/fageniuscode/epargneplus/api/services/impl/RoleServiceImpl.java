package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.RoleDTO;
import com.fageniuscode.epargneplus.api.entities.Role;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.RoleMapper;
import com.fageniuscode.epargneplus.api.repositories.RoleRepository;
import com.fageniuscode.epargneplus.api.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    @Override
    public List<RoleDTO> getAllRoles() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDTO getRoleById(int roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("role.notfound", roleId)));
        return roleMapper.roleToRoleDTO(role);
    }

    @Transactional
    @Override
    public RoleDTO createRole(@Valid RoleDTO roleDTO) {
        Role role = roleMapper.roleDTOToRole(roleDTO);
        if (roleRepository.existsById(Math.toIntExact(role.getId()))) {
            throw new RequestException(getMessage("role.exists", role.getId()), HttpStatus.BAD_REQUEST);
        }
        Role savedRole = roleRepository.save(role);
        return roleMapper.roleToRoleDTO(savedRole);
    }

    @Transactional
    @Override
    public RoleDTO updateRole(int roleId, @Valid RoleDTO roleDTO){
        return roleRepository.findById(roleId)
                .map(entity -> {
                    roleDTO.setId(roleId);
                    return roleMapper.roleToRoleDTO(roleRepository.save(roleMapper.roleDTOToRole(roleDTO)));
                }).orElseThrow(() -> new EntityNotFoundException(getMessage("role.notfound", roleId)));
    }

    @Transactional
    @Override
    public void deleteRole(int id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(getMessage("role.errordeletion", id), HttpStatus.CONFLICT);
        }
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}

