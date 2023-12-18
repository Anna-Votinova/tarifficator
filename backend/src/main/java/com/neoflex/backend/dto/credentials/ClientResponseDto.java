package com.neoflex.backend.dto.credentials;

import com.neoflex.backend.dto.credentials.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Клиент")
public class ClientResponseDto {

        @Schema(description = "Идентификатор клиента", example = "1")
        private Long id;

        @Schema(description = "Идентификатор клиента в банке", example = "1")
        private Long bankId;

        @Schema(description = "Фамилия", example = "Orlova")
        private String lastname;

        @Schema(description = "Имя", example = "Ekaterina")
        private String firstname;

        @Schema(description = "Отчество", example = "Alexandrovna")
        private String middleName;

        @Schema(description = "Дата рождения", example = "1990-01-12")
        private LocalDate birthDate;

        @Schema(description = "Номер паспорта", example = "1234567890")
        private String passportNumber;

        @Schema(description = "Место рождения", example = "Moscow")
        private String birthPlace;

        @Schema(description = "Номер телефона", example = "7ХХХХХХХХХХ")
        private String phoneNumber;

        @Schema(description = "Электронный почтовый ящик", example = "anyvotinova@yandex.ru")
        private String email;

        @Schema(description = "Роль", example = "ADMIN")
        private Role role;

        @Schema(description = "Адрес регистрации")
        private AddressDto registrationAddress;

        @Schema(description = "Адрес проживания")
        private AddressDto residentialAddress;
}