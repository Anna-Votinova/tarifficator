package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.NullOrBlankUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class BankAppValidator implements Validator {
    @Override
    public void validate(ClientDto clientDto) {
        log.info("Validate client bank id = [{}]; lastname = [{}]; firstname = [{}]; middleName = [{}], " +
                        "birth date = [{}], passport number = [{}]", clientDto.bankId(), clientDto.lastname(),
                clientDto.firstname(), clientDto.middleName(), clientDto.birthDate(), clientDto.passportNumber());

        if (isNotValid(clientDto)) {
            throw new InvalidCredentialsException("Bank_id, фамилия, имя, отчество, дата рождения и номер паспорта не " +
                    "должны быть пустыми или равны null.");
        }
        log.info("All credentials for client are correct");
    }

    @Override
    public boolean isNotValid(ClientDto clientDto) {
        return Objects.isNull(clientDto.bankId())
                || NullOrBlankUtil.isNullOrBlank(clientDto.lastname())
                || NullOrBlankUtil.isNullOrBlank(clientDto.firstname())
                || NullOrBlankUtil.isNullOrBlank(clientDto.middleName())
                || Objects.isNull(clientDto.birthDate())
                || NullOrBlankUtil.isNullOrBlank(clientDto.passportNumber());
    }
}
