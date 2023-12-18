package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.NullOrBlankUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MobileAppValidator implements Validator {
    @Override
    public void validate(ClientRequestDto clientRequestDto) {
        log.info("Validate client phone number equals [{}]", clientRequestDto.phoneNumber());
        if (isNotValid(clientRequestDto)) {
            throw new InvalidCredentialsException("Мобильный телефон не должен быть пустым или равным null.");
        }
        log.info("Phone number equals {} is valid", clientRequestDto.phoneNumber());
    }

    @Override
    public boolean isNotValid(ClientRequestDto clientRequestDto) {
        return NullOrBlankUtil.isNullOrBlank(clientRequestDto.phoneNumber());
    }
}
