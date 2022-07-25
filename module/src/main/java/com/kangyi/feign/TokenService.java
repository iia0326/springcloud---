package com.kangyi.feign;

import com.kangyi.feign.fallback.TokenServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "oauth-service",fallback = TokenServiceImpl.class)
public interface TokenService {

    @GetMapping("/oauth/token")
    String getToken(@RequestParam(name = "username") String username,
                    @RequestParam(name = "password") String password,
                    @RequestParam(name = "grant_type") String grant_type,
                    @RequestParam(name = "client_id") String client_id,
                    @RequestParam(name = "client_secret") String client_secret,
                    @RequestParam(name = "scope") String scope);

}
