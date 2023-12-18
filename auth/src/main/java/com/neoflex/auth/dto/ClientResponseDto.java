package com.neoflex.auth.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponseDto {
        private Long id;
        private String bankId;
        private String lastname;
        private String firstname;
        private String middleName;
        private LocalDate birthDate;
        private String passportNumber;
        private String birthPlace;
        private String phoneNumber;
        private String email;
        private String role;
        private AddressDto registrationAddress;
        private AddressDto residentialAddress;
}
