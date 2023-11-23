package com.neoflex.credentials.service.util;

import com.neoflex.credentials.dto.ClientDto;

public interface Validator {
    void validate(ClientDto clientDto);
    boolean isNotValid(ClientDto clientDto);
}
