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
        log.info("Check client address region = {}; city = {}; street = {}; building number = {}, apartment number = {}, " +
                        "address type = {}", addressDto.region(), addressDto.city(), addressDto.street(),
                addressDto.buildingNumber(), addressDto.apartmentNumber(), addressDto.addressType());

        if (addressDto.region().isBlank() || addressDto.city().isBlank()
                || addressDto.street().isBlank() || addressDto.buildingNumber().isBlank()
                || addressDto.apartmentNumber().isBlank()|| Objects.isNull(addressDto.addressType())) {
            throw new InvalidCredentialsException("Bank_id, фамилия, имя, отчество, дата рождения и номер паспорта не " +
                    "должны быть пустыми.");
        }
        log.info("All parameters of the address = {} are correct", addressDto);

    }
}
