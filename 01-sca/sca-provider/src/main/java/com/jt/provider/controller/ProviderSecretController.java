package com.jt.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ProviderSecretController {
    @Value("${app.secret:CCCCC}")
    private String secret;

    @GetMapping("/provider/secret")
    public String doGetSecret(){
        return "the secret is "+secret;
    }
}
