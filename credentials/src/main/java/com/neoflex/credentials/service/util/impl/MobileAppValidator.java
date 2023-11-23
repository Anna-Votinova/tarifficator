package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.NullOrBlankUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MobileAppValidator implements Validator {
    @Override
    public void validate(ClientDto clientDto) {
        log.info("Validate client phone number equals [{}]", clientDto.phoneNumber());
        if (isNotValid(clientDto)) {
            throw new InvalidCredentialsException("Мобильный телефон не должен быть пустым или равным null.");
        }
        log.info("Phone number equals {} is valid", clientDto.phoneNumber());
    }

    @Override
    public boolean isNotValid(ClientDto clientDto) {
        return NullOrBlankUtil.isNullOrBlank(clientDto.phoneNumber());
    }
}
