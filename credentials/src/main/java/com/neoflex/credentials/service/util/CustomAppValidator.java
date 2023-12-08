package com.neoflex.credentials.service.util;

import com.neoflex.credentials.config.ApplicationProperties;
import com.neoflex.credentials.config.GlobalVariables;
import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.exeption.ApplicationNotSupportedException;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomAppValidator {

    public static final String CLIENT_BANK_ID = "bankId";
    public static final String CLIENT_BIRTH_DATE = "birthDate";
    public static final String CLIENT_PASSPORT_NUMBER = "passportNumber";
    public static final String CLIENT_BIRTH_PLACE = "birthPlace";
    public static final String CLIENT_REGISTRATION_ADDRESS = "registrationAddress";
    public static final String CLIENT_RESIDENTIAL_ADDRESS = "residentialAddress";

    private final ApplicationProperties applicationProperties;

    public void validate(String applicationType, ClientRequestDto clientRequestDto) {
        log.info("Find application {} in properties", applicationType);
        List<String> fields = getCheckingFields(applicationType.toLowerCase());
        log.info("Fields for validation: {}", fields);

        if (fields.isEmpty()) {
            throw new ApplicationNotSupportedException("Приложение " + applicationType + " не поддерживается");
        }
        validateByFields(clientRequestDto, fields);
    }

    private List<String> getCheckingFields(String applicationType) {
        log.info("Get fields for validation form the new apps configuration map with size {}",
                applicationProperties.getNewApplications().size());
        return applicationProperties.getNewApplications().getOrDefault(applicationType, Collections.emptyList());
    }

    private void validateByFields(ClientRequestDto clientRequestDto, List<String> fields) {
        if (fields.contains(CLIENT_BANK_ID)) {
            log.info("Check client bank id = [{}]", clientRequestDto.bankId());
            validateNoStringField(clientRequestDto.bankId());
        }
        if (fields.contains(GlobalVariables.CLIENT_LASTNAME)) {
            log.info("Check client lastname = [{}]", clientRequestDto.lastname());
            validateStringField(clientRequestDto.lastname());
        }
        if (fields.contains(GlobalVariables.CLIENT_FIRSTNAME)) {
            log.info("Check client firstname = [{}]", clientRequestDto.firstname());
            validateStringField(clientRequestDto.firstname());
        }
        if (fields.contains(GlobalVariables.CLIENT_MIDDLE_NAME)) {
            log.info("Check client middle name = [{}]", clientRequestDto.middleName());
            validateStringField(clientRequestDto.middleName());
        }
        if (fields.contains(CLIENT_BIRTH_DATE)) {
            log.info("Check client birth date = [{}]", clientRequestDto.birthDate());
            validateNoStringField(clientRequestDto.birthDate());
        }
        if (fields.contains(CLIENT_PASSPORT_NUMBER)) {
            log.info("Check client passport number = [{}]", clientRequestDto.passportNumber());
            validateStringField(clientRequestDto.passportNumber());
        }
        if (fields.contains(CLIENT_BIRTH_PLACE)) {
            log.info("Check client birth place = [{}]", clientRequestDto.birthPlace());
            validateStringField(clientRequestDto.birthPlace());
        }
        if (fields.contains(GlobalVariables.CLIENT_PHONE_NUMBER)) {
            log.info("Check client phone number = [{}]", clientRequestDto.phoneNumber());
            validateStringField(clientRequestDto.phoneNumber());
        }
        if (fields.contains(GlobalVariables.CLIENT_EMAIL)) {
            log.info("Check client email = [{}]", clientRequestDto.email());
            validateStringField(clientRequestDto.email());
        }
        if (fields.contains(CLIENT_REGISTRATION_ADDRESS)) {
            log.info("Check client registration address = [{}]", clientRequestDto.registrationAddress());
            validateNoStringField(clientRequestDto.registrationAddress());
            CheckAddressUtil.checkAddress(clientRequestDto.registrationAddress());
        }
        if (fields.contains(CLIENT_RESIDENTIAL_ADDRESS)) {
            log.info("Check client residential address = [{}]", clientRequestDto.residentialAddress());
            validateNoStringField(clientRequestDto.residentialAddress());
            CheckAddressUtil.checkAddress(clientRequestDto.residentialAddress());
        }
    }

    private void validateNoStringField(Object field) {
        if (Objects.isNull(field)) {
            throw new InvalidCredentialsException("Поле не должно быть пустым");
        }
    }

    private void validateStringField(String field) {
        if (NullOrBlankUtil.isNullOrBlank(field)) {
            throw new InvalidCredentialsException("Поле не должно быть пустым или равно null");
        }
    }
}
