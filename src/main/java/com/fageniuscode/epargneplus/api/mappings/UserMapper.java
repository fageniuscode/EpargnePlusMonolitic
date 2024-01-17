package com.fageniuscode.epargneplus.api.mappings;
import com.fageniuscode.epargneplus.api.entities.dto.UserDTO;
import com.fageniuscode.epargneplus.api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "id")
    UserDTO userToUserDTO(User user);
    @Mapping(target = "id", source = "id")
    User userDTOToUser(UserDTO userDTO);
}

