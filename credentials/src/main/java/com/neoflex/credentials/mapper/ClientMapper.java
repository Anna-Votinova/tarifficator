package com.neoflex.credentials.mapper;

import com.neoflex.credentials.dto.*;
import com.neoflex.credentials.entity.Address;
import com.neoflex.credentials.entity.Client;
import com.neoflex.credentials.entity.Password;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientMapper {

    public static Client toClient(ClientRequestDto clientRequestDto) {
        Client client = new Client();

        client.setBankId(clientRequestDto.bankId());
        client.setLastname(clientRequestDto.lastname());
        client.setFirstname(clientRequestDto.firstname());
        client.setMiddleName(clientRequestDto.middleName());
        client.setBirthDate(clientRequestDto.birthDate());
        client.setPassportNumber(clientRequestDto.passportNumber());
        client.setBirthPlace(clientRequestDto.birthPlace());
        client.setPhoneNumber(clientRequestDto.phoneNumber());
        client.setEmail(clientRequestDto.email());
        client.setPassword(new Password(clientRequestDto.password()));
        client.setRegistrationAddress(Objects.isNull(clientRequestDto.registrationAddress())
                ? null
                : toAddress(clientRequestDto.registrationAddress()));
        client.setResidentialAddress(Objects.isNull(clientRequestDto.residentialAddress())
                ? null
                : toAddress(clientRequestDto.residentialAddress()));

        return client;
    }

    public static ClientResponseDto toClientResponseDto(Client client) {

        return new ClientResponseDto(
                client.getId(),
                client.getBankId(),
                client.getLastname(),
                client.getFirstname(),
                client.getMiddleName(),
                client.getBirthDate(),
                client.getPassportNumber(),
                client.getBirthPlace(),
                client.getPhoneNumber(),
                client.getEmail(),
                client.getRole(),
                addressDto(client.getRegistrationAddress()),
                addressDto(client.getResidentialAddress())
        );
    }

    public static List<ClientResponseDto> mapToClients(List<Client> receivedClients) {
        return receivedClients.stream()
                .map(ClientMapper::toClientResponseDto)
                .toList();
    }

    public static ClientSecurityDto toClientSecurityDto(Client client, String login) {
        return new ClientSecurityDto(
                client.getId(),
                login,
                Objects.isNull(client.getPassword())
                        ? null
                        : client.getPassword().getUserPassword(),
                client.getRole().name()
        );
    }

    private static Address toAddress(AddressDto addressDto) {
        Address address = new Address();

        address.setRegion(addressDto.region());
        address.setCity(addressDto.city());
        address.setStreet(addressDto.street());
        address.setBuildingNumber(addressDto.buildingNumber());
        address.setApartmentNumber(addressDto.apartmentNumber());
        address.setAddressType(addressDto.addressType());

        return address;
    }

    private static AddressDto addressDto(Address address) {

        if (Objects.isNull(address)) {
            return AddressDto.builder().build();
        }

        return AddressDto.builder()
                .id(address.getId())
                .region(address.getRegion())
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .apartmentNumber(address.getApartmentNumber())
                .addressType(address.getAddressType())
                .build();
    }
 }
