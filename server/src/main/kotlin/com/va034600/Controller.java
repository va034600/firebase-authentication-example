package com.va034600;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/pub/test")
    public String pub() {
        return "pub";
    }

    @GetMapping("/auth/test")
    public String auth() {
        return "auth";
    }
}