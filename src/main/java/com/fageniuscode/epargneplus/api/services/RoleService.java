package com.fageniuscode.epargneplus.api.services;

import com.fageniuscode.epargneplus.api.entities.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    // Liste tous les rôles
    List<RoleDTO> getAllRoles();
    // Récupère un rôle par son ID
    RoleDTO getRoleById(int roleId);
    // Crée un nouveau rôle
    RoleDTO createRole(RoleDTO roleDTO);
    // Met à jour les informations d'un rôle
    RoleDTO updateRole(int roleId, RoleDTO roleDTO);
    // Supprime un rôle par son ID
    void deleteRole(int roleId);
}
