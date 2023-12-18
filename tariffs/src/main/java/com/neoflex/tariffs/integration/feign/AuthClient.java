package com.neoflex.tariffs.integration.feign;

import com.neoflex.tariffs.dto.AccessToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "auth", url = "${applications.url.auth}",
        configuration = AuthClientConfiguration.class)
public interface AuthClient {

    @GetMapping("/verify")
    String verify(@RequestHeader("Authorization") AccessToken accessToken, @RequestParam String service);
}
