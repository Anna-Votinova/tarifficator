package com.neoflex.credentials.service.util;

import com.neoflex.credentials.config.ApplicationProperties;
import com.neoflex.credentials.dto.AddressDto;
import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.dto.enums.AddressType;
import com.neoflex.credentials.exeption.ApplicationNotSupportedException;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomAppValidatorTest {

    private Map<String, List<String>> applicationFieldsMap;
    private String application;

    @Mock
    private ApplicationProperties applicationProperties;
    @InjectMocks
    private CustomAppValidator customAppValidator;

    @BeforeEach
    void setUp() {
        applicationFieldsMap = getApplicationFieldsMap();
        application = "all";
    }

    @Test
    void shouldOk_WhenValidCredentialsAndCustomApplicationTypeAll() {
        var clientDto = getClientDtoBuilder().build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        customAppValidator.validate(application, clientDto);

        verify(applicationProperties, times(2)).getNewApplications();
    }

    @Test
    void shouldThrowException_WhenApplicationTypeUnknown() {
        var applicationUniversity = "university";
        var clientDto = ClientRequestDto.builder().build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(ApplicationNotSupportedException.class,
                () -> customAppValidator.validate(applicationUniversity, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullBankId() {
        var clientDto = getClientDtoBuilder()
                .bankId(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullLastName() {
        var clientDto = getClientDtoBuilder()
                .lastname(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankLastName(String blankLastName) {
        var clientDto = getClientDtoBuilder()
                .lastname(blankLastName)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullFirstName() {
        var clientDto = getClientDtoBuilder()
                .firstname(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankFirstName(String blankFirstName) {
        var clientDto = getClientDtoBuilder()
                .firstname(blankFirstName)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullMiddleName() {
        var clientDto = getClientDtoBuilder()
                .middleName(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankMiddleName(String blankMiddleName) {
        var clientDto = getClientDtoBuilder()
                .middleName(blankMiddleName)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullBirthDate() {
        var clientDto = getClientDtoBuilder()
                .birthDate(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullPassportNumber() {
        var clientDto = getClientDtoBuilder()
                .passportNumber(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankPassportNumber(String blankPassportNumber) {
        var clientDto = getClientDtoBuilder()
                .passportNumber(blankPassportNumber)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullBirthPlace() {
        var clientDto = getClientDtoBuilder()
                .birthPlace(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankBirthPlace(String blankBirthPlace) {
        var clientDto = getClientDtoBuilder()
                .birthPlace(blankBirthPlace)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullPhoneNumber() {
        var clientDto = getClientDtoBuilder()
                .phoneNumber(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankPhoneNumber(String blankPhoneNumber) {
        var clientDto = getClientDtoBuilder()
                .phoneNumber(blankPhoneNumber)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullEmail() {
        var clientDto = getClientDtoBuilder()
                .email(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeCustomAndBlankEmail(String blankPhoneEmail) {
        var clientDto = getClientDtoBuilder()
                .email(blankPhoneEmail)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullRegistrationAddress() {
        var clientDto = getClientDtoBuilder()
                .registrationAddress(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeCustomAndNullResidentialAddress() {
        var clientDto = getClientDtoBuilder()
                .residentialAddress(null)
                .build();

        when(applicationProperties.getNewApplications()).thenReturn(applicationFieldsMap);

        assertThrows(InvalidCredentialsException.class, () -> customAppValidator.validate(application, clientDto));
    }

    private AddressDto.AddressDtoBuilder getAddressBuilder() {
        return AddressDto.builder()
                .region("Voronezh region")
                .city("Voronezh")
                .street("Street")
                .buildingNumber("104б")
                .apartmentNumber("564")
                .addressType(AddressType.REGISTRATION);
    }

    private ClientRequestDto.ClientRequestDtoBuilder getClientDtoBuilder() {
        var residentialAddress = getAddressBuilder()
                .addressType(AddressType.RESIDENTIAL)
                .build();

        return ClientRequestDto.builder()
                .bankId("12323")
                .lastname("Orlova")
                .firstname("Olga")
                .middleName("Igorevna")
                .birthDate(LocalDate.of(1990, 11, 11))
                .passportNumber("1234567890")
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .email("email111@ya.ru")
                .registrationAddress(getAddressBuilder().build())
                .residentialAddress(residentialAddress);
    }

    private Map<String, List<String>> getApplicationFieldsMap() {
        Map<String, List<String>> applicationFieldsMap = new HashMap<>();
        applicationFieldsMap.put("all", List.of(
                "bankId",
                "lastname",
                "firstname",
                "middleName",
                "birthDate",
                "passportNumber",
                "birthPlace",
                "phoneNumber",
                "email",
                "registrationAddress",
                "residentialAddress"
        ));
        return applicationFieldsMap;
    }
}