package com.fageniuscode.epargneplus.api.mappings;

import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.StatusTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusTransactionMapper {

    @Mapping(target = "id", source = "id")
    StatusTransactionDTO statusTransactionToStatusTransactionDTO(StatusTransaction statusTransaction);

    @Mapping(target = "id", source = "id")
    StatusTransaction statusTransactionDTOToStatusTransaction(StatusTransactionDTO statusTransactionDTO);

}
