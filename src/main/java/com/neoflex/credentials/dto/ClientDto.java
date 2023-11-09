package com.neoflex.credentials.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Клиент")
public record ClientDto(
        @Schema(description = "Идентификатор клиента", example = "1")
        Long id,

        @Schema(description = "Идентификатор клиента в банке", example = "1")
        Long bankId,

        @Schema(description = "Фамилия", example = "Orlova")
        String lastname,

        @Schema(description = "Имя", example = "Ekaterina")
        String firstname,

        @Schema(description = "Отчество", example = "Alexandrovna")
        String middleName,

        @Schema(description = "Дата рождения", example = "1990-01-12")
        LocalDate birthDate,

        @Schema(description = "Номер паспорта", example = "1234567890")
        String passportNumber,

        @Schema(description = "Место рождения", example = "Moscow")
        String birthdayPlace,

        @Schema(description = "Номер телефона", example = "7ХХХХХХХХХХ")
        String phoneNumber,

        @Schema(description = "Электронный почтовый ящик", example = "anyvotinova@yandex.ru")
        String email,

        @Schema(description = "Адрес регистрации")
        AddressDto registrationAddress,

        @Schema(description = "Адрес проживания")
        AddressDto residentialAddress
) {}
