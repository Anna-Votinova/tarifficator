package com.neoflex.credentials.service.util.impl;

import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.service.util.NullOrBlankUtil;
import com.neoflex.credentials.service.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailAppValidator implements Validator {
    @Override
    public void validate(ClientRequestDto clientRequestDto) {
        log.info("Validate client name equals [{}] and email equals [{}]", clientRequestDto.firstname(), clientRequestDto.email());
        if (isNotValid(clientRequestDto)) {
            throw new InvalidCredentialsException("Имя и почта не должны быть пустыми или равны null.");
        }
        log.info("Name equals {} and email equals {} are valid", clientRequestDto.firstname(), clientRequestDto.email());
    }

    @Override
    public boolean isNotValid(ClientRequestDto clientRequestDto) {
        return NullOrBlankUtil.isNullOrBlank(clientRequestDto.firstname())
                || NullOrBlankUtil.isNullOrBlank(clientRequestDto.email());
    }
}
