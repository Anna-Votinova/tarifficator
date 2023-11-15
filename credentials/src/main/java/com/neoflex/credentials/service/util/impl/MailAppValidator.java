package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.NullOrBlankUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailAppValidator implements Validator {
    @Override
    public void validate(ClientDto clientDto) {
        log.info("Validate client name equals [{}] and email equals [{}]", clientDto.firstname(), clientDto.email());
        if (isNotValid(clientDto)) {
            throw new InvalidCredentialsException("Имя и почта не должны быть пустыми или равны null.");
        }
        log.info("Name equals {} and email equals {} are valid", clientDto.firstname(), clientDto.email());
    }

    @Override
    public boolean isNotValid(ClientDto clientDto) {
        return NullOrBlankUtil.isNullOrBlank(clientDto.firstname())
                || NullOrBlankUtil.isNullOrBlank(clientDto.email());
    }
}
