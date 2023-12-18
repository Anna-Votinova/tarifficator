package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.CheckAddressUtil;
import com.neoflex.credentials.service.util.NullOrBlankUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GosuslugiAppValidator implements Validator {
    @Override
    public void validate(ClientRequestDto clientRequestDto) {
        log.info("Validate client bank id = [{}]; lastname = [{}]; firstname = [{}]; middleName = [{}]; " +
                        "birth date = [{}]; passport number = [{}]; birth place = [{}]; phone number = [{}]; " +
                        "registration address = [{}]", clientRequestDto.bankId(), clientRequestDto.lastname(), clientRequestDto.firstname(),
                clientRequestDto.middleName(), clientRequestDto.birthDate(), clientRequestDto.passportNumber(), clientRequestDto.birthPlace(),
                clientRequestDto.phoneNumber(), clientRequestDto.registrationAddress());

        if (isNotValid(clientRequestDto)) {
            throw new InvalidCredentialsException("Все поля, кроме почты и адреса проживания, не должны быть пустыми " +
                    "или равны null.");
        }
        CheckAddressUtil.checkAddress(clientRequestDto.registrationAddress());

        log.info("All credentials for client are correct");
    }

    @Override
    public boolean isNotValid(ClientRequestDto clientRequestDto) {
        return Objects.isNull(clientRequestDto.bankId())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.lastname())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.firstname())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.middleName())
                || Objects.isNull(clientRequestDto.birthDate())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.passportNumber())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.birthPlace())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.phoneNumber())
                || Objects.isNull(clientRequestDto.registrationAddress());
    }
}
