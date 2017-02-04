package io.github.augustoerico.services

import io.github.augustoerico.verticles.Channel
import io.vertx.core.Future
import io.vertx.groovy.core.Vertx

class Bye {

    Vertx vertx

    Bye(Vertx vertx) {
        this.vertx = vertx
    }

    def sayBye(String name, Closure successFn) {
        vertx.eventBus().send(Channel.BYE.name(), name, { Future reply ->
            if (reply.succeeded()) {
                def result = reply.result()
                println 'Success'
                successFn(result)
            } else {
                println reply.cause().message
            }
        })
    }

    static Bye create(Vertx vertx) {
        new Bye(vertx)
    }

}
