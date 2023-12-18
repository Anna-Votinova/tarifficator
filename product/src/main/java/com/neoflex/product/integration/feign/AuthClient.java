package com.neoflex.product.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth", url = "${applications.url.auth}",
        configuration = AuthClientConfiguration.class)
public interface AuthClient {

    @GetMapping("/verify")
    String verify(@RequestHeader("Authorization") String accessToken, @RequestParam String service);
}
