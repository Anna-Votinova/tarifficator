package com.neoflex.credentials.service.util;

import com.neoflex.credentials.dto.ClientRequestDto;

public interface Validator {
    void validate(ClientRequestDto clientRequestDto);
    boolean isNotValid(ClientRequestDto clientRequestDto);
}
