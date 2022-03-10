package com.opotapov.stonks

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}