package com.neoflex.auth.integration.credentials.config;

import com.neoflex.auth.config.GlobalVariables;
import com.neoflex.auth.dto.ClientRequestDto;
import com.neoflex.auth.dto.ClientResponseDto;
import com.neoflex.auth.dto.ClientSecurityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "credentials", url = "${application.integration.url.credentials}",
        configuration = CredentialsClientConfiguration.class)
public interface CredentialsClient {

    @PostMapping("/create")
    ClientResponseDto createClient(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER)
                                   String applicationType, @RequestBody ClientRequestDto clientRequestDto);

    @GetMapping(value = "/find")
    ClientSecurityDto getClientByLogin(@RequestParam String login);

}
