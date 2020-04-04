package com.ita.azure.demo

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HelloWorldControllerTest {

    @Test
    fun sayHelloWorld() {
        val helloWorldController = HelloWorldController()
        assertEquals("Hello World", helloWorldController.sayHelloWorld())
    }
}
