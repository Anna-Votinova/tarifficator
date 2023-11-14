package com.neoflex.credentials.service.util;

import com.neoflex.credentials.config.ApplicationProperties;
import com.neoflex.credentials.config.GlobalVariables;
import com.neoflex.credentials.dto.ClientDto;
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

    public void validate(String applicationType, ClientDto clientDto) {
        log.info("Find application {} in properties", applicationType);
        List<String> fields = getCheckingFields(applicationType.toLowerCase());

        if (fields.isEmpty()) {
            throw new ApplicationNotSupportedException("Приложение " + applicationType + " не поддерживается");
        }
        validateByFields(clientDto, fields);
    }

    private List<String> getCheckingFields(String applicationType) {
        log.info("Get fields for validation form the new apps configuration map with size {}",
                applicationProperties.getNewApplications().size());
        return applicationProperties.getNewApplications().getOrDefault(applicationType, Collections.emptyList());
    }

    private void validateByFields(ClientDto clientDto, List<String> fields) {
        if (fields.contains(CLIENT_BANK_ID)) {
            log.info("Check client bank id = {}", clientDto.bankId());
            validateNoStringField(clientDto.bankId());
        }
        if (fields.contains(GlobalVariables.CLIENT_LASTNAME)) {
            log.info("Check client lastname = {}", clientDto.lastname());
            validateStringField(clientDto.lastname());
        }
        if (fields.contains(GlobalVariables.CLIENT_FIRSTNAME)) {
            log.info("Check client firstname = {}", clientDto.firstname());
            validateStringField(clientDto.firstname());
        }
        if (fields.contains(GlobalVariables.CLIENT_MIDDLE_NAME)) {
            log.info("Check client middle name = {}", clientDto.middleName());
            validateStringField(clientDto.middleName());
        }
        if (fields.contains(CLIENT_BIRTH_DATE)) {
            log.info("Check client birth date = {}", clientDto.birthDate());
            validateNoStringField(clientDto.birthDate());
        }
        if (fields.contains(CLIENT_PASSPORT_NUMBER)) {
            log.info("Check client passport number = {}", clientDto.passportNumber());
            validateStringField(clientDto.passportNumber());
        }
        if (fields.contains(CLIENT_BIRTH_PLACE)) {
            log.info("Check client birth place = {}", clientDto.birthPlace());
            validateStringField(clientDto.birthPlace());
        }
        if (fields.contains(GlobalVariables.CLIENT_PHONE_NUMBER)) {
            log.info("Check client phone number = {}", clientDto.phoneNumber());
            validateStringField(clientDto.phoneNumber());
        }
        if (fields.contains(GlobalVariables.CLIENT_EMAIL)) {
            log.info("Check client email = {}", clientDto.email());
            validateStringField(clientDto.email());
        }
        if (fields.contains(CLIENT_REGISTRATION_ADDRESS)) {
            log.info("Check client registration address = {}", clientDto.registrationAddress());
            validateNoStringField(clientDto.registrationAddress());
            CheckAddressUtil.checkAddress(clientDto.registrationAddress());
        }
        if (fields.contains(CLIENT_RESIDENTIAL_ADDRESS)) {
            log.info("Check client residential address = {}", clientDto.residentialAddress());
            validateNoStringField(clientDto.residentialAddress());
            CheckAddressUtil.checkAddress(clientDto.residentialAddress());
        }
    }

    private void validateNoStringField(Object field) {
        if (Objects.isNull(field)) {
            throw new InvalidCredentialsException("Поле не должно быть пустым");
        }
    }

    private void validateStringField(String field) {
        if (field.isBlank()) {
            throw new InvalidCredentialsException("Поле не должно быть пустым");
        }
    }
}
