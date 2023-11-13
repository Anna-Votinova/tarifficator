package com.neoflex.credentials.service;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.dto.ClientFieldsDto;
import com.neoflex.credentials.dto.enums.ApplicationType;

import com.neoflex.credentials.exeption.NotCompletedImplementationException;
import com.neoflex.credentials.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final ClientRepository clientRepository;

    public ClientDto createClient(ApplicationType applicationType, ClientDto clientDto) {
        throw new NotCompletedImplementationException("createClient");
    }

    public ClientDto getClientById(Long id) {
        throw new NotCompletedImplementationException("getClientById");
    }

    public List<ClientDto> getClientByParameters(ClientFieldsDto clientFieldsDto) {
        throw new NotCompletedImplementationException("getClientByParameters");
    }
}
