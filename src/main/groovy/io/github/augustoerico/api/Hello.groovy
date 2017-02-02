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

    def sayHello(String name, Closure successFn) {
        println 'Sending message to event bus'
        vertx.eventBus().send(Channel.HELLO.name(), name, { Future reply ->
            if (reply.succeeded()) {
                def result = reply.result()
                println 'Success'
                successFn(result)
            } else {
                def cause = reply.cause()
                println cause.message
            }
        })
    }
}
