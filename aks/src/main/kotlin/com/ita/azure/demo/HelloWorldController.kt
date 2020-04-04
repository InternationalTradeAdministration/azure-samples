package com.ita.azure.demo

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class HelloWorldController {

    @GetMapping("/api/hello-world")
    fun sayHelloWorld(): String {
        return "Hello World"
    }
}
