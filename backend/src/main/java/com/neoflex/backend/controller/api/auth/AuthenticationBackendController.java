package com.neoflex.backend.controller.api.auth;


import com.neoflex.backend.config.GlobalVariables;
import com.neoflex.backend.dto.AccessToken;
import com.neoflex.backend.dto.auth.LoginRequest;
import com.neoflex.backend.dto.credentials.ClientRequestDto;
import com.neoflex.backend.dto.credentials.ClientResponseDto;
import com.neoflex.backend.integration.auth.AuthClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller",
        description = "Позволяет зарегистрироваться и войти в систему")
public class AuthenticationBackendController {

    private final AuthClient authClient;

    @Operation(summary = "Регистрация юзера",
            description = "Создает учетные записи клиента на основе данных, пришедших из того или иного сервиса. " +
                    "Возможные варианты сервисов: mail, mobile, bank, gosuslugi")
    @PostMapping("/register")
    public ClientResponseDto register(@RequestHeader(value = GlobalVariables.CREDENTIALS_SOURCE_HEADER)
                                      String applicationType, @RequestBody ClientRequestDto clientRequestDto) {
        return authClient.register(applicationType, clientRequestDto);
    }

    @Operation(summary = "Вход в систему",
            description = "Возвращает токен для дальнейшей работы со службами приложения")
    @PostMapping("/authenticate")
    public AccessToken authenticate(@RequestBody LoginRequest loginRequest) {
        return authClient.authenticate(loginRequest);
    }
}
