package com.fageniuscode.epargneplus.api.mappings;

import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.TypeTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TypeTransactionMapper {

    @Mapping(target = "id", source = "id")
    TypeTransactionDTO typeTransactionToTypeTransactionDTO(TypeTransaction typeTransaction);

    @Mapping(target = "id", source = "id")
    TypeTransaction typeTransactionDTOToTypeTransaction(TypeTransactionDTO typeTransactionDTO);

}
