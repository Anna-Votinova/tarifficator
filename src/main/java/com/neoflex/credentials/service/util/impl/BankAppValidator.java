package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class BankAppValidator implements Validator {
    @Override
    public void validate(ClientDto clientDto) {
        log.info("Validate client bank id = {}; lastname = {}; firstname = {}; middleName = {}, birth date = {}, " +
                "passport number = {}", clientDto.bankId(), clientDto.lastname(), clientDto.firstname(),
                clientDto.middleName(), clientDto.birthDate(), clientDto.passportNumber());

        if (Objects.isNull(clientDto.bankId()) || clientDto.lastname().isBlank() || clientDto.firstname().isBlank()
                || clientDto.middleName().isBlank() || Objects.isNull(clientDto.birthDate())
                || clientDto.passportNumber().isBlank()) {
            throw new InvalidCredentialsException("Bank_id, фамилия, имя, отчество, дата рождения и номер паспорта не " +
                    "должны быть пустыми.");
        }
        log.info("All credentials for client = {} are correct", clientDto);
    }
}
