package com.fageniuscode.epargneplus.api.mappings;

import com.fageniuscode.epargneplus.api.entities.dto.RoleDTO;
import com.fageniuscode.epargneplus.api.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "id", source = "id")
    RoleDTO roleToRoleDTO(Role role);
    @Mapping(target = "id", source = "id")
    Role roleDTOToRole(RoleDTO roleDTO);
}

