package com.neoflex.backend.integration.credentials;

import com.neoflex.backend.config.GlobalVariables;
import com.neoflex.backend.dto.credentials.ClientRequestDto;
import com.neoflex.backend.dto.credentials.ClientResponseDto;
import com.neoflex.backend.dto.credentials.enums.Role;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@FeignClient(value = "credentials", url = "${applications.url.credentials}",
        configuration = CredentialsClientConfiguration.class)
public interface CredentialsClient {

    //убрать - перенести в аутх
    @PostMapping(value = "/create")
    ClientResponseDto createClient(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER) String applicationType,
                                   @RequestBody ClientRequestDto clientRequestDto);

    @GetMapping(value = "/find/{id}")
    ClientResponseDto getClientById(@PathVariable Long id);

    @GetMapping(value = "/find/parameters")
    List<ClientResponseDto> getClientByParameters(@RequestParam String lastname, @RequestParam String firstname,
                                                  @RequestParam String middleName, @RequestParam String phoneNumber,
                                                  @RequestParam String email);

    @PutMapping("/role/{id}")
    void changeAuthority(@Positive @PathVariable @Parameter(description = "Идентификатор клиента",
            example = "1", required = true) Long id, @Parameter(description = "Роль") @RequestParam Role role);

}
