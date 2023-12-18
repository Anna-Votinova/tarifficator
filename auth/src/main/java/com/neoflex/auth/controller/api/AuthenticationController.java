package com.neoflex.auth.controller.api;

import com.neoflex.auth.config.GlobalVariables;
import com.neoflex.auth.config.SecurityProperties;
import com.neoflex.auth.dto.ClientRequestDto;
import com.neoflex.auth.dto.ClientResponseDto;
import com.neoflex.auth.dto.LoginRequest;
import com.neoflex.auth.dto.AccessToken;
import com.neoflex.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final SecurityProperties securityProperties;
    private final AuthenticationService authenticationService;

    /**
     *
     * @param clientRequestDto - info for registration
     * @return access token
     */
    @PostMapping("/register")
    public ClientResponseDto register(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER)
                                      String applicationType, @RequestBody ClientRequestDto clientRequestDto) {
        return authenticationService.register(applicationType, clientRequestDto);
    }

    /**
     * @param loginRequest - info for login
     * @return access token
     */
    @PostMapping("/authenticate")
    public AccessToken authenticate(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticate(loginRequest);
    }

    /**
     * @param accessToken - token to verify
     * @return login
     */
    @GetMapping("/verify")
    @PreAuthorize("@authenticationController.checkServices(#service) eq true ")
    public String verify(@RequestHeader("Authorization") String accessToken, @RequestParam @P("service") String service) {
        log.info("Got the request to verify token {} from service {}", accessToken, service);
        return authenticationService.getLogin();
    }

    public boolean checkServices(String service) {
        log.info("Invoke method checkServices from @PreAuthorize for service {}. Search it in services list {}",
                service, securityProperties.getServices());
        return securityProperties.getServices().contains(service);
    }
}
