package com.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping(value = "/public")
    public String publicApi() {
        return "pub";
    }

    @GetMapping(value = "/private")
    public String privateApi() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = (String) (authentication.getPrincipal());

        return "username: " + username;
    }
}
