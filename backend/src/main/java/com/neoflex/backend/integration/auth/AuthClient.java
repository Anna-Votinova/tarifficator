package com.neoflex.backend.integration.auth;

import com.neoflex.backend.config.GlobalVariables;
import com.neoflex.backend.dto.AccessToken;
import com.neoflex.backend.dto.auth.LoginRequest;
import com.neoflex.backend.dto.credentials.ClientRequestDto;
import com.neoflex.backend.dto.credentials.ClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "auth", url = "${applications.url.auth}",
        configuration = AuthClientConfiguration.class)
public interface AuthClient {

    @PostMapping("/register")
    ClientResponseDto register(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER)
                                      String applicationType, @RequestBody ClientRequestDto clientRequestDto);

    @PostMapping("/authenticate")
    AccessToken authenticate(@RequestBody LoginRequest loginRequest);
}
