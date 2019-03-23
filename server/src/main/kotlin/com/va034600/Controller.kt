package com.va034600

import org.springframework.web.bind.annotation .GetMapping
import org.springframework.web.bind.annotation .RestController

@RestController
class Controller {
    @GetMapping("/pub/test")
    fun pub(): String {
        return "pub"
    }

    @GetMapping("/auth/test")
    fun auth(): String {
        return "auth"
    }
}