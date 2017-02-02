package io.github.augustoerico.api

import io.vertx.core.Future
import io.vertx.groovy.core.Vertx

class Hello {

    final Vertx vertx

    Hello(Vertx vertx) {
        this.vertx = vertx
    }

    static Hello create(Vertx vertx) {
        new Hello(vertx)
    }

    def sayHello(String name) {
        def message = "Hello, $name".toString()
        println 'Sending message to event bus'
        vertx.eventBus().send(Channel.HELLO.name(), message, { Future reply ->
            if (reply.succeeded()) {
                println 'Succeded'
            } else {
                def cause = reply.cause()
                println cause.message
            }
        })
    }
}
