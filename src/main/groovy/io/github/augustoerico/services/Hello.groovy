package io.github.augustoerico.services

import io.github.augustoerico.verticles.Channel
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

    def sayHello(String name, Closure successFn, Closure failureFn = { }) {
        println 'Sending message to event bus'
        vertx.eventBus().send(Channel.HELLO.name(), name, { Future reply ->
            def threadId = Thread.currentThread().id
            println "Hello.sayHello: $threadId"
            if (reply.succeeded()) {
                def result = reply.result()
                println 'Success'
                successFn(result)
            } else {
                def cause = reply.cause()
                println cause.message
                println 'Exception on event bus timeout'
                failureFn(cause.message)
            }
        })
    }
}
