package com.neoflex.credentials.dto;

import com.neoflex.credentials.dto.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Адрес регистрации или место жительтсва клиента")
@Builder
public record AddressDto(
        @Schema(description = "Идентификатор адреса", example = "1")
        Long id,

        @Schema(description = "Регион", example = "Voronezh region")
        String region,

        @Schema(description = "Город", example = "Voronezh")
        String city,

        @Schema(description = "Улица", example = "Лизюкова")
        String street,

        @Schema(description = "Номер дома с литерой, дробью, корпусом или строением", example = "104б")
        String buildingNumber,

        @Schema(description = "Номер квартиры", example = "564")
        String apartmentNumber,

        @Schema(description = "Типа адреса", example = "REGISTRATION")
        AddressType addressType
) {}
