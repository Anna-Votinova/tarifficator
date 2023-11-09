package com.neoflex.credentials.controller.api;

import com.neoflex.credentials.dto.ClientDto;
import com.neoflex.credentials.dto.ClientFieldsDto;
import com.neoflex.credentials.dto.enums.ApplicationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/credentials")
@Slf4j
@Tag(name = "Credentials Controller",
        description = "Создает и читает учетные данные")
public class CredentialsController {

    @Operation(summary = "Создание учетной записи",
            description = "Создает учетные записи клиента на основе данных, пришедших из того или иного сервиса")
    @PostMapping
    public ClientDto createClient(@RequestHeader("x-Source") ApplicationType applicationType,
                                  @RequestBody ClientDto clientDto) {
        return null;
    }

    @Operation(summary = "Чтение учетной записи по id",
            description = "Возвращает данные клиента по введенному в запросе id")
    @GetMapping("/{id}")
    public ClientDto getClientById(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id) {
        return null;
    }
    @Operation(summary = "Поиск учетной записи по полям",
            description = "Возвращает данные клиента или клиентов в зависимости от введенных параметров")
    @GetMapping("/parameters")
    public List<ClientDto> getClientByParameters(@RequestBody ClientFieldsDto clientFieldsDto) {
        return null;
    }
}
