package com.neoflex.credentials.service;

import com.neoflex.credentials.config.GlobalVariables;
import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.dto.ClientFieldsDto;

import com.neoflex.credentials.dto.ClientResponseDto;
import com.neoflex.credentials.dto.ClientSecurityDto;
import com.neoflex.credentials.dto.enums.Role;
import com.neoflex.credentials.entity.Client;
import com.neoflex.credentials.exeption.ClientNotFoundException;
import com.neoflex.credentials.exeption.ValidationException;
import com.neoflex.credentials.mapper.ClientMapper;
import com.neoflex.credentials.repository.ClientRepository;
import com.neoflex.credentials.repository.Criteria;
import com.neoflex.credentials.repository.CustomClientRepository;
import com.neoflex.credentials.repository.QueryOperator;
import com.neoflex.credentials.service.util.CustomAppValidator;
import com.neoflex.credentials.service.util.Validator;
import com.neoflex.credentials.service.util.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialService {

    private final ClientRepository clientRepository;
    private final CustomClientRepository customClientRepository;
    private final CustomAppValidator customAppValidator;

    public ClientResponseDto createClient(String applicationType, ClientRequestDto clientRequestDto) {
        checkApplicationTypeNotBlank(applicationType);
        validateCredentials(applicationType, clientRequestDto);
        Client client = ClientMapper.toClient(clientRequestDto);

        Client savedClient = clientRepository.save(client);
        log.info("Saved client equals {}", savedClient);
        return ClientMapper.toClientResponseDto(savedClient);
    }

    public ClientResponseDto getClientById(Long clientId) {
        Client client = getClientByIdOrThrowException(clientId);
        log.info("Got the client from the repository: {}", client);
        return ClientMapper.toClientResponseDto(client);
    }

    public ClientSecurityDto getClientByLogin(String login) {
        Client client = clientRepository.findByLogin(login).
                orElseThrow(() -> new ClientNotFoundException("Клиент с логином " + login + " не существует"));
        log.info("Got the client from the repository: {}", client);
        return ClientMapper.toClientSecurityDto(client, login);
    }

    public List<ClientResponseDto> getClientByParameters(ClientFieldsDto clientFieldsDto) {
        List<Criteria> criteria = getCriteria(clientFieldsDto);
        if(criteria.isEmpty()) {
            throw new ValidationException("Укажите хотя бы одно поле для поиска клиента");
        }

        List<Client> receivedClients = customClientRepository.getClientsByParameters(criteria);
        if (receivedClients.isEmpty()) {
            log.info("Clients with received parameters not found");
            return Collections.emptyList();
        }
        log.info("Received clients {}", receivedClients.size());
        return ClientMapper.mapToClients(receivedClients);
    }

    public void changeRole(Long clientId, Role role) {
        Client client = getClientByIdOrThrowException(clientId);
        client.setRole(role);
        clientRepository.save(client);
        log.info("Saved client with id {} with new role: {}", clientId, client.getRole());
    }

    private void validateCredentials(String applicationType, ClientRequestDto clientRequestDto) {
        log.info("Validate credentials for application {}", applicationType);
        Validator validator = ValidatorFactory.getValidator(applicationType);

        if (Objects.isNull(validator)) {
            log.info("Default validator for application {} does not exist", applicationType);
            customAppValidator.validate(applicationType, clientRequestDto);
        } else {
            validator.validate(clientRequestDto);
        }
    }

    private List<Criteria> getCriteria(ClientFieldsDto clientFieldsDto) {
        List<Criteria> criteria = new ArrayList<>();

        Optional.ofNullable(clientFieldsDto.lastname())
                .ifPresent(l -> criteria.add(
                        new Criteria(GlobalVariables.CLIENT_LASTNAME, QueryOperator.EQUALS, l)));
        Optional.ofNullable(clientFieldsDto.firstname())
                .ifPresent(f -> criteria.add(
                        new Criteria(GlobalVariables.CLIENT_FIRSTNAME, QueryOperator.EQUALS, f)));
        Optional.ofNullable(clientFieldsDto.middleName())
                .ifPresent(m -> criteria.add(
                        new Criteria(GlobalVariables.CLIENT_MIDDLE_NAME, QueryOperator.EQUALS, m)));
        Optional.ofNullable(clientFieldsDto.phoneNumber())
                .ifPresent(p -> criteria.add(
                        new Criteria(GlobalVariables.CLIENT_PHONE_NUMBER, QueryOperator.EQUALS, p)));
        Optional.ofNullable(clientFieldsDto.email())
                .ifPresent(e -> criteria.add(
                        new Criteria(GlobalVariables.CLIENT_EMAIL, QueryOperator.EQUALS, e)));

        log.info("Criteria list for search clients = {}", criteria);
        return criteria;
    }

    private void checkApplicationTypeNotBlank(String applicationType) {
        log.info("Check application type [{}] on emptiness", applicationType);
        if (applicationType.isBlank()) {
            throw new ValidationException("Название приложения должно быть указано");
        }
    }

    private Client getClientByIdOrThrowException(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Клиент с id " + clientId + " не существует"));
    }
}
