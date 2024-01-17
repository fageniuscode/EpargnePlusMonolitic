package com.fageniuscode.epargneplus.api.mappings;

import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import com.fageniuscode.epargneplus.api.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "id", source = "id")
    WalletDTO walletToWalletDTO(Wallet wallet);

    @Mapping(target = "id", source = "id")
    Wallet walletDTOToWallet(WalletDTO walletDTO);

}
