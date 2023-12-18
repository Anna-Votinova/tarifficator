package com.neoflex.auth.dto;

import com.neoflex.auth.dto.enums.AddressType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressDto {
        private Long id;
        private String region;
        private String city;
        private String street;
        private String buildingNumber;
        private String apartmentNumber;
        private AddressType addressType;
}
