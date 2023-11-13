package com.neoflex.credentials.controller.api;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.dto.ClientFieldsDto;
import com.neoflex.credentials.service.CredentialService;
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
            description = "Создает учетные записи клиента на основе данных, пришедших из того или иного сервиса")
    @PostMapping("/create")
    public ClientDto createClient(@RequestHeader(APPLICATION) String applicationType,
                                  @RequestBody ClientDto clientDto) {
        log.info("Got the request for creating client from application = {}", applicationType);
        return credentialService.createClient(applicationType, clientDto);
    }

    @Operation(summary = "Чтение учетной записи по id",
            description = "Возвращает данные клиента по введенному в запросе id")
    @GetMapping("/find/{id}")
    public ClientDto getClientById(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id) {
        log.info("Got the request for getting client with id = {}", id);
        return credentialService.getClientById(id);
    }
    @Operation(summary = "Поиск учетной записи по полям",
            description = "Возвращает данные клиента или клиентов в зависимости от введенных параметров")
    @GetMapping("/find/parameters")
    public List<ClientDto> getClientByParameters(@RequestBody ClientFieldsDto clientFieldsDto) {
        log.info("Got the request for getting client(s) by parameters from DTO = {}", clientFieldsDto);
        return credentialService.getClientByParameters(clientFieldsDto);
    }
}
