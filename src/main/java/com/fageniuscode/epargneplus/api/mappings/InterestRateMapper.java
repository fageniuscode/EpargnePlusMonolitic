package com.fageniuscode.epargneplus.api.mappings;
import com.fageniuscode.epargneplus.api.entities.dto.InterestRateDTO;
import com.fageniuscode.epargneplus.api.entities.InterestRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InterestRateMapper {

    @Mapping(target = "id", source = "id")
    InterestRateDTO interestRateToInterestRateDTO(InterestRate interestRate);

    @Mapping(target = "id", source = "id")
    InterestRate interestRateDTOToInterestRate(InterestRateDTO interestRateDTO);

}
