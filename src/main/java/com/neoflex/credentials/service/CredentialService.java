package com.neoflex.credentials.service;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.dto.ClientFieldsDto;

import com.neoflex.credentials.entity.Client;
import com.neoflex.credentials.exeption.ClientNotFoundException;
import com.neoflex.credentials.exeption.ValidationException;
import com.neoflex.credentials.mapper.ClientMapper;
import com.neoflex.credentials.repository.ClientRepository;
import com.neoflex.credentials.service.util.CustomAppValidator;
import com.neoflex.credentials.service.util.Validator;
import com.neoflex.credentials.service.util.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialService {

    private final ClientRepository clientRepository;
    private final CustomAppValidator customAppValidator;

    public ClientDto createClient(String applicationType, ClientDto clientDto) {
        checkApplicationType(applicationType);
        validateCredentials(applicationType, clientDto);
        Client client = ClientMapper.toClient(clientDto);

        Client savedClient = clientRepository.save(client);
        log.info("Saved client equals {}", savedClient);
        return ClientMapper.toClientDto(savedClient);
    }

    public ClientDto getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Клиент с id " + clientId + " не существует"));
        log.info("Got the client from the repository: {}", client);
        return ClientMapper.toClientDto(client);
    }

    public List<ClientDto> getClientByParameters(ClientFieldsDto clientFieldsDto) {
        throw new ValidationException("getClientByParameters");
    }

    private void validateCredentials(String applicationType, ClientDto clientDto) {
        log.info("Validate credentials for application {}", applicationType);
        Validator validator = ValidatorFactory.getValidator(applicationType);

        if (Objects.isNull(validator)) {
            log.info("Default validator for application {} does not exist", applicationType);
            customAppValidator.validate(applicationType, clientDto);
        }
        validator.validate(clientDto);
    }
    private void checkApplicationType(String applicationType) {
        log.info("Check application type {} on emptiness", applicationType);
        if (applicationType.isBlank()) {
            throw new ValidationException("Название приложения должно быть указано");
        }
    }
}
