package com.fageniuscode.epargneplus.api.mappings;
import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", source = "id")
    TransactionDTO transactionToTransactionDTO(Transaction transaction);

    @Mapping(target = "id", source = "id")
    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);

}
