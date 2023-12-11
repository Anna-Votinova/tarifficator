package com.neoflex.auth.integration.credentials.config;

import com.neoflex.auth.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "credentials", url = "${application.integration.url.credentials}",
        configuration = CredentialsClientConfiguration.class)
public interface CredentialsClient {

    @GetMapping(value = "/find")
    User getClientByLogin(@RequestParam String login);
}
