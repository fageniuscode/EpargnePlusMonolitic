package com.fageniuscode.epargneplus.api.mappings;

import com.fageniuscode.epargneplus.api.entities.dto.AuditLogDTO;
import com.fageniuscode.epargneplus.api.entities.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    @Mapping(target = "id", source = "id")
    AuditLogDTO auditLogToAuditLogDTO(AuditLog auditLog);

    @Mapping(target = "id", source = "id")
    AuditLog auditLogDTOToAuditLog(AuditLogDTO auditLogDTO);
}
