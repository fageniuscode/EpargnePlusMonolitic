package com.fageniuscode.epargneplus.api.mappings;

import com.fageniuscode.epargneplus.api.entities.dto.NotificationDTO;
import com.fageniuscode.epargneplus.api.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "id", source = "id")
    NotificationDTO notificationToNotificationDTO(Notification Notification);

    @Mapping(target = "id", source = "id")
    Notification notificationDTOToNotification(NotificationDTO notificationDTO);
}
