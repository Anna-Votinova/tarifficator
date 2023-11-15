package com.neoflex.credentials.service.util;

import com.neoflex.credentials.dto.AddressDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class CheckAddressUtil {

    public static void checkAddress(AddressDto addressDto) {
        log.info("Check client {} address", addressDto.addressType());

        if (isNotValid(addressDto)) {
            throw new InvalidCredentialsException("Регион, город, улица, номера дома и квартиры, тип адреса должны " +
                    "быть заполнены.");
        }
        log.info("All parameters of the address = {} are correct", addressDto);
    }

    private static boolean isNotValid(AddressDto addressDto) {
        return NullOrBlankUtil.isNullOrBlank(addressDto.region())
                || NullOrBlankUtil.isNullOrBlank(addressDto.city())
                || NullOrBlankUtil.isNullOrBlank(addressDto.street())
                || NullOrBlankUtil.isNullOrBlank(addressDto.buildingNumber())
                || NullOrBlankUtil.isNullOrBlank(addressDto.apartmentNumber())
                || Objects.isNull(addressDto.addressType());
    }
}
