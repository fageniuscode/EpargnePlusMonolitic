package com.fageniuscode.epargneplus.api.mappings;
import com.fageniuscode.epargneplus.api.entities.dto.PermissionDTO;
import com.fageniuscode.epargneplus.api.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "id", source = "id")
    PermissionDTO permissionToPermissionDTO(Permission permission);
    @Mapping(target = "id", source = "id")
    Permission permissionDTOToPermission(PermissionDTO permissionDTO);
}
