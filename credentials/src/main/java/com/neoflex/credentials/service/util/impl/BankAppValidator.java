package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientRequestDto;
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
    public void validate(ClientRequestDto clientRequestDto) {
        log.info("Validate client bank id = [{}]; lastname = [{}]; firstname = [{}]; middleName = [{}], " +
                        "birth date = [{}], passport number = [{}]", clientRequestDto.bankId(), clientRequestDto.lastname(),
                clientRequestDto.firstname(), clientRequestDto.middleName(), clientRequestDto.birthDate(), clientRequestDto.passportNumber());

        if (isNotValid(clientRequestDto)) {
            throw new InvalidCredentialsException("Bank_id, фамилия, имя, отчество, дата рождения и номер паспорта не " +
                    "должны быть пустыми или равны null.");
        }
        log.info("All credentials for client are correct");
    }

    @Override
    public boolean isNotValid(ClientRequestDto clientRequestDto) {
        return Objects.isNull(clientRequestDto.bankId())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.lastname())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.firstname())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.middleName())
                || Objects.isNull(clientRequestDto.birthDate())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.passportNumber());
    }
}
