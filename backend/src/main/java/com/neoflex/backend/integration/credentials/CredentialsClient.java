package com.neoflex.backend.integration.credentials;

import com.neoflex.backend.config.GlobalVariables;
import com.neoflex.backend.dto.credentials.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "credentials", url = "${applications.url.credentials}",
        configuration = CredentialsClientConfiguration.class)
public interface CredentialsClient {

    @PostMapping(value = "/create")
    ClientDto createClient(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER) String applicationType,
                           @RequestBody ClientDto clientDto);

    @GetMapping(value = "/find/{id}")
    ClientDto getClientById(@PathVariable Long id);

    @GetMapping(value = "/find/parameters")
    List<ClientDto> getClientByParameters(@RequestParam String lastname, @RequestParam String firstname,
                                          @RequestParam String middleName, @RequestParam String phoneNumber,
                                          @RequestParam String email);

}
