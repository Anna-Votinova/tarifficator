package com.neoflex.credentials.service;

import com.neoflex.credentials.dto.AddressDto;
import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.dto.ClientFieldsDto;
import com.neoflex.credentials.dto.ClientResponseDto;
import com.neoflex.credentials.dto.enums.AddressType;
import com.neoflex.credentials.entity.Client;
import com.neoflex.credentials.exeption.ClientNotFoundException;
import com.neoflex.credentials.exeption.InvalidCredentialsException;
import com.neoflex.credentials.exeption.ValidationException;
import com.neoflex.credentials.repository.ClientRepository;
import com.neoflex.credentials.repository.CustomClientRepository;
import com.neoflex.credentials.service.util.CustomAppValidator;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CredentialServiceTest {

    @Mock
    private CustomAppValidator customAppValidator;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CustomClientRepository customClientRepository;
    @InjectMocks
    private CredentialService credentialService;

    @AfterEach
    public void verifyInteractions() {
        verifyNoMoreInteractions(
                clientRepository,
                customClientRepository
        );
    }

    @Test
    void shouldCreateClient_WhenValidCredentialsAndApplicationTypeMail() {
        var application = "mail";
        var client = new Client();
        var clientDto = ClientRequestDto.builder()
                .firstname("Anna")
                .email("email@ya.ru")
                .build();

        when(clientRepository.save(any())).thenReturn(client);

        credentialService.createClient(application, clientDto);

        verify(clientRepository).save(any());
    }

    @Test
    void shouldCreateClient_WhenValidCredentialsAndApplicationTypeMobile() {
        var application = "mobile";
        var client = new Client();
        var clientDto = ClientRequestDto.builder()
                .phoneNumber("79999999999")
                .build();

        when(clientRepository.save(any())).thenReturn(client);

        credentialService.createClient(application, clientDto);

        verify(clientRepository).save(any());
    }

    @Test
    void shouldCreateClient_WhenValidCredentialsAndApplicationTypeBank() {
        var application = "bank";
        var client = new Client();
        var clientDto = getClientDtoBuilder().build();

        when(clientRepository.save(any())).thenReturn(client);

        credentialService.createClient(application, clientDto);

        verify(clientRepository).save(any());
    }

    @Test
    void shouldCreateClient_WhenValidCredentialsAndApplicationTypeGosuslugi() {
        var application = "gosuslugi";
        var client = new Client();
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        when(clientRepository.save(any())).thenReturn(client);

        credentialService.createClient(application, clientDto);

        verify(clientRepository).save(any());
    }

    @Test
    void shouldInvokeCustomValidator_WhenCustomApplicationTypeAll() {
        var application = "all";
        var client = new Client();
        var clientDto = ClientRequestDto.builder().build();

        when(clientRepository.save(any())).thenReturn(client);

        credentialService.createClient(application, clientDto);

        verify(customAppValidator).validate(any(), any());
        verify(clientRepository).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeIsBlank(String blankApplicationType) {
        var clientDto = ClientRequestDto.builder().build();

        assertThrows(ValidationException.class, () -> credentialService.createClient(blankApplicationType, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeMailAndNullFirstName() {
        var application = "mail";
        var clientDto = ClientRequestDto.builder()
                .firstname(null)
                .email("email@ya.ru")
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeMailAndBlankFirstName(String blankFirstname) {
        var application = "mail";
        var clientDto = ClientRequestDto.builder()
                .firstname(blankFirstname)
                .email("email@ya.ru")
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeMailAndNullEmail() {
        var application = "mail";
        var clientDto = ClientRequestDto.builder()
                .firstname("Ann")
                .email(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeMailAndBlankEmail(String blankEmail) {
        var application = "mail";
        var clientDto = ClientRequestDto.builder()
                .firstname("Ann")
                .email(blankEmail)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeMobileAndNullPhoneNumber() {
        var application = "mobile";
        var clientDto = ClientRequestDto.builder()
                .phoneNumber(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeMobileAndBlankPhoneNumber(String blankPhoneNumber) {
        var application = "mobile";
        var clientDto = ClientRequestDto.builder()
                .phoneNumber(blankPhoneNumber)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeBankAndNullBankId() {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .bankId(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeBankAndNullLastName() {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .lastname(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeBankAndNullFirstName() {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .firstname(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeBankAndNullMiddleName() {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .middleName(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeBankAndNullBirthDate() {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .birthDate(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeBankAndNullPassportNumber() {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .passportNumber(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeBankAndBlankLastName(String blankLastName) {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .lastname(blankLastName)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeBankAndBlankFirstName(String blankFirstName) {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .firstname(blankFirstName)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeBankAndBlankMiddleName(String blankMiddleName) {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .middleName(blankMiddleName)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeBankAndBlankPassportNumber(String blankPassportNumber) {
        var application = "bank";
        var clientDto = getClientDtoBuilder()
                .passportNumber(blankPassportNumber)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullBankId() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .bankId(null)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullLastName() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .lastname(null)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankLastName(String blankLastName) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .lastname(blankLastName)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullFirstName() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .firstname(null)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankFirstName(String blankFirstName) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .firstname(blankFirstName)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullMiddleName() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .middleName(null)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankMiddleName(String blankMiddleName) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .middleName(blankMiddleName)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullBirthDate() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .birthDate(null)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullPassportNumber() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .passportNumber(null)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankPassportNumber(String blankPassportNumber) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .passportNumber(blankPassportNumber)
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullBirthPlace() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .birthPlace(null)
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankBirthPlace(String blankBirthPlace) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .birthPlace(blankBirthPlace)
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullPhoneNumber() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber(null)
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankPhoneNumber(String blankPhoneNumber) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder().build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber(blankPhoneNumber)
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddress() {
        var application = "gosuslugi";
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(null)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddressRegion() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .region(null)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankRegistrationAddressRegion(String blankRegion) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .region(blankRegion)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddressCity() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .city(null)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankRegistrationAddressCity(String blankCity) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .city(blankCity)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddressStreet() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .street(null)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankRegistrationAddressStreet(String blankStreet) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .street(blankStreet)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddressBuildingNumber() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .buildingNumber(null)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankRegistrationBuildingNumber(String blankBuilding) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .buildingNumber(blankBuilding)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddressApartmentNumber() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .apartmentNumber(null)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldThrowException_WhenApplicationTypeGosuslugiAndBlankRegistrationApartmentNumber(String blankApartment) {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .apartmentNumber(blankApartment)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldThrowException_WhenApplicationTypeGosuslugiAndNullRegistrationAddressAddressTyper() {
        var application = "gosuslugi";
        var registrationAddress = getAddressBuilder()
                .addressType(null)
                .build();
        var clientDto = getClientDtoBuilder()
                .birthPlace("Moscow")
                .phoneNumber("79999999999")
                .registrationAddress(registrationAddress)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> credentialService.createClient(application, clientDto));
    }

    @Test
    void shouldReturnClient_WhenValidInput() {
        var clientId = 1L;
        var client = new Client();

        when(clientRepository.findById(any())).thenReturn(Optional.of(client));

        credentialService.getClientById(clientId);

        verify(clientRepository).findById(any());
    }

    @Test
    void shouldThrowException_WhenInvalidInput() {
        var clientId = -1L;

        when(clientRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> credentialService.getClientById(clientId));
    }

    @Test
    void shouldReturnClientsByParameters_WhenValidInput() {
        var client = new Client();
        var clientFieldsDto = new ClientFieldsDto(
                "Orlova",
                "Olga",
                "Igorevna",
                "79999999999",
                "email13@ya.ru"
        );

        when(customClientRepository.getClientsByParameters(any())).thenReturn(List.of(client));

        credentialService.getClientByParameters(clientFieldsDto);

        verify(customClientRepository).getClientsByParameters(any());
    }

    @Test
    void shouldThrowException_WhenNoFieldForSearchExists() {
        var clientFieldsDto = new ClientFieldsDto(
                null,
                null,
                null,
                null,
                null
        );

        assertThrows(ValidationException.class, () -> credentialService.getClientByParameters(clientFieldsDto));
    }

    @Test
    void shouldReturnEmptyList_WhenNoClientExists() {
        var clientFieldsDto = new ClientFieldsDto(
                "Orlova",
                "Olga",
                "Igorevna",
                "79999999999",
                "email13@ya.ru"
        );

        when(customClientRepository.getClientsByParameters(any())).thenReturn(Collections.emptyList());

        List<ClientResponseDto> receivedClients = credentialService.getClientByParameters(clientFieldsDto);

        assertEquals(0, receivedClients.size());
        verify(customClientRepository).getClientsByParameters(any());
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
        return ClientRequestDto.builder()
                .bankId("12323")
                .lastname("Orlova")
                .firstname("Olga")
                .middleName("Igorevna")
                .birthDate(LocalDate.of(1990, 11, 11))
                .passportNumber("1234567890");
    }
}