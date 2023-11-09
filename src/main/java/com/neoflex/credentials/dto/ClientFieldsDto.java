package com.neoflex.credentials.dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Поля для поиска учетных записей")
public record ClientFieldsDto(
        @Schema(description = "Фамилия", example = "Orlova")
        String lastname,
        @Schema(description = "Имя", example = "Ekaterina")
        String firstname,
        @Schema(description = "Отчество", example = "Alexandrovna")
        String middleName,
        @Schema(description = "Номер телефона", example = "7ХХХХХХХХХХ")
        String phoneNumber,
        @Schema(description = "Электронный почтовый ящик", example = "anyvotinova@yandex.ru")
        String email
) {}
