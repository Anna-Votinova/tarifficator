package com.neoflex.backend.controller.api.credentials;

import com.neoflex.backend.config.GlobalVariables;
import com.neoflex.backend.dto.credentials.ClientRequestDto;
import com.neoflex.backend.dto.credentials.ClientResponseDto;
import com.neoflex.backend.dto.credentials.enums.Role;
import com.neoflex.backend.integration.credentials.CredentialsClient;
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
@RequestMapping(path = "/api/credentials")
@Slf4j
@Tag(name = "Credentials Controller",
        description = "Создает и читает учетные данные")
@RequiredArgsConstructor
@Validated
public class CredentialsBackendController {

    private final CredentialsClient credentialsClient;

    @Hidden
    @Operation(summary = "Создание учетной записи",
            description = "Создает учетные записи клиента на основе данных, пришедших из того или иного сервиса. " +
                    "Возможные варианты сервисов: mail, mobile, bank, gosuslugi")
    @PostMapping("/create")
    public ClientResponseDto createClient(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER) String applicationType,
                                          @RequestBody ClientRequestDto clientRequestDto) {
        log.info("Got the request for creating client from application = {}", applicationType);
        return credentialsClient.createClient(applicationType, clientRequestDto);
    }

    @Operation(summary = "Чтение учетной записи по id",
            description = "Возвращает данные клиента по введенному в запросе id")
    @GetMapping("/find/{id}")
    public ClientResponseDto getClientById(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id) {
        log.info("Got the request for getting client with id = {}", id);
        return credentialsClient.getClientById(id);
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
        log.info("Got the request for getting client(s) by parameters lastname {}, firstname {}, middle name {}, " +
                        "phone number {}, email {}", lastname, firstname, middleName, phoneNumber, email);
        return credentialsClient.getClientByParameters(lastname, firstname, middleName, phoneNumber, email);
    }

    @Operation(summary = "Изменение роли юзера",
            description = "Позволяет изменить роль юзера")
    @PutMapping("/role/{id}")
    public void changeAuthority(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id, @Parameter(description = "Роль") @RequestParam Role role) {
        log.info("Got the request for changing role = {} for client with id {}", role, id);
        credentialsClient.changeAuthority(id, role);
    }
}
