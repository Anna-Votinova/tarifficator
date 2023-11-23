package com.neoflex.credentials.mapper;

import com.neoflex.credentials.dto.AddressDto;
import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.entity.Address;
import com.neoflex.credentials.entity.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientMapper {

    public static Client toClient(ClientDto clientDto) {
        Client client = new Client();

        client.setId(clientDto.id());
        client.setBankId(clientDto.bankId());
        client.setLastname(clientDto.lastname());
        client.setFirstname(clientDto.firstname());
        client.setMiddleName(clientDto.middleName());
        client.setBirthDate(clientDto.birthDate());
        client.setPassportNumber(clientDto.passportNumber());
        client.setBirthPlace(clientDto.birthPlace());
        client.setPhoneNumber(clientDto.phoneNumber());
        client.setEmail(clientDto.email());
        client.setRegistrationAddress(Objects.isNull(clientDto.registrationAddress())
                ? null
                : toAddress(clientDto.registrationAddress()));
        client.setResidentialAddress(Objects.isNull(clientDto.residentialAddress())
                ? null
                : toAddress(clientDto.residentialAddress()));

        return client;
    }

    private static Address toAddress(AddressDto addressDto) {
        Address address = new Address();

        address.setId(addressDto.id());
        address.setRegion(addressDto.region());
        address.setCity(addressDto.city());
        address.setStreet(addressDto.street());
        address.setBuildingNumber(addressDto.buildingNumber());
        address.setApartmentNumber(addressDto.apartmentNumber());
        address.setAddressType(addressDto.addressType());

        return address;
    }

    public static ClientDto toClientDto(Client client) {

        return new ClientDto(
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
                addressDto(client.getRegistrationAddress()),
                addressDto(client.getResidentialAddress())
        );
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

    public static List<ClientDto> mapToClients(List<Client> receivedClients) {
        return receivedClients.stream()
                .map(ClientMapper::toClientDto)
                .toList();
    }
 }
