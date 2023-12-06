package com.neoflex.backend.dto.credentials;

import com.neoflex.backend.dto.credentials.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Schema(description = "Адрес регистрации или место жительства клиента")
public class AddressDto {

        @Schema(description = "Идентификатор адреса", example = "1")
        private Long id;

        @Schema(description = "Регион", example = "Voronezh region")
        private String region;

        @Schema(description = "Город", example = "Voronezh")
        private String city;

        @Schema(description = "Улица", example = "Лизюкова")
        private String street;

        @Schema(description = "Номер дома с литерой, дробью, корпусом или строением", example = "104б")
        private String buildingNumber;

        @Schema(description = "Номер квартиры", example = "564")
        private String apartmentNumber;

        @Schema(description = "Типа адреса", example = "REGISTRATION")
        private AddressType addressType;
}
