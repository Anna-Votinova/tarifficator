package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.CheckAddressUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GosuslugiAppValidator implements Validator {
    @Override
    public void validate(ClientDto clientDto) {
        log.info("Validate client bank id = {}; lastname = {}; firstname = {}; middleName = {}; birth date = {}; " +
                        "passport number = {}; birth place = {}; phone number = {}; registration address = {}",
                clientDto.bankId(), clientDto.lastname(), clientDto.firstname(), clientDto.middleName(),
                clientDto.birthDate(), clientDto.passportNumber(), clientDto.birthPlace(), clientDto.phoneNumber(),
                clientDto.registrationAddress());

        if (Objects.isNull(clientDto.bankId()) || clientDto.lastname().isBlank() || clientDto.firstname().isBlank()
                || clientDto.middleName().isBlank() || Objects.isNull(clientDto.birthDate())
                || clientDto.passportNumber().isBlank() || clientDto.birthPlace().isBlank()
                || clientDto.phoneNumber().isBlank() || Objects.isNull(clientDto.registrationAddress())) {
            throw new InvalidCredentialsException("Все поля, кроме почты и адреса проживания, не должны быть пустыми.");
        }
        CheckAddressUtil.checkAddress(clientDto.registrationAddress());

        log.info("All credentials for client = {} are correct", clientDto);
    }
}
