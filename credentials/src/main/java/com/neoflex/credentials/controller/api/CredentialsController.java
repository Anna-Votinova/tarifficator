package com.neoflex.credentials.controller.api;

import com.neoflex.credentials.dto.ClientRequestDto;
import com.neoflex.credentials.dto.ClientFieldsDto;
import com.neoflex.credentials.dto.ClientResponseDto;
import com.neoflex.credentials.dto.ClientSecurityDto;
import com.neoflex.credentials.dto.enums.Role;
import com.neoflex.credentials.service.CredentialService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/credentials")
@Slf4j
@Tag(name = "Credentials Controller",
        description = "Создает и читает учетные данные")
@RequiredArgsConstructor
@Validated
public class CredentialsController {

    private static final String APPLICATION = "x-Source";

    private final CredentialService credentialService;

    @Operation(summary = "Создание учетной записи",
            description = "Создает учетные записи клиента на основе данных, пришедших из того или иного сервиса. " +
                    "Возможные варианты сервисов: mail, mobile, bank, gosuslugi")
    @PostMapping("/create")
    public ClientResponseDto createClient(@RequestHeader(value = APPLICATION) String applicationType,
                                  @RequestBody ClientRequestDto clientRequestDto) {
        log.info("Got the request for creating client from application = {}", applicationType);
        return credentialService.createClient(applicationType, clientRequestDto);
    }

    @Operation(summary = "Чтение учетной записи по id",
            description = "Возвращает данные клиента по введенному в запросе id")
    @GetMapping("/find/{id}")
    public ClientResponseDto getClientById(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id) {
        log.info("Got the request for getting client with id = {}", id);
        return credentialService.getClientById(id);
    }

    @Operation(summary = "Поиск учетной записи по полям",
            description = "Возвращает данные клиента или клиентов в зависимости от введенных параметров")
    @GetMapping("/find/parameters")
    public List<ClientResponseDto> getClientByParameters(
            @Parameter(description = "Фамилия", example = "Orlova") @RequestParam(required = false) String lastname,
            @Parameter(description = "Имя", example = "Ekaterina") @RequestParam(required = false) String firstname,
            @Parameter(description = "Отчество", example = "Alexandrovna")
            @RequestParam(required = false) String middleName,
            @Parameter(description = "Номер телефона", example = "79999999999")
            @RequestParam(required = false) String phoneNumber,
            @Parameter(description = "Электронный почтовый ящик", example = "anyvotinova@yandex.ru")
            @RequestParam(required = false) String email
            ) {
        ClientFieldsDto clientFieldsDto = new ClientFieldsDto(lastname, firstname, middleName, phoneNumber, email);
        log.info("Got the request for getting client(s) by parameters from DTO = {}", clientFieldsDto);
        return credentialService.getClientByParameters(clientFieldsDto);
    }

    @Hidden
    @Operation(summary = "Получение юзера по логину",
            description = "Возвращает данные юзера для проведения аутентификации")
    @GetMapping("/find")
    public ClientSecurityDto getClientByLogin(@Parameter(description = "Логин") @RequestParam String login) {
        log.info("Got the request for getting client with login = {}", login);
        return credentialService.getClientByLogin(login);
    }

    @PutMapping("/role/{id}")
    public void changeAuthority(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id, @Parameter(description = "Роль") @RequestParam Role role) {
        log.info("Got the request for changing role = {} for client with id {}", role, id);
        credentialService.changeRole(id, role);
    }
}
