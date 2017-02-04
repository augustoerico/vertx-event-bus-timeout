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

    def sayHello(String name, Closure successFn, Closure failureFn = {}) {
        println 'Sending message to event bus'
        vertx.eventBus().send(
                Channel.HELLO.name(),
                name,
                { Future reply ->

                    def thread = Thread.currentThread()
                    println 'Hello.sayHello: handling reply'
                    println "Hello.sayHello: thread: $thread.id | $thread.name"

                    if (reply.succeeded()) {
                        def result = reply.result()
                        println 'Hello.sayHello: success'
                        successFn(result)
                    } else {
                        def message = reply.cause().message
                        println "Hello.sayHello: failed: $message"
                        failureFn(message)
                    }
                })
    }
}
