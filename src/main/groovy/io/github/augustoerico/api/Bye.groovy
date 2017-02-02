package io.github.augustoerico.api

import io.vertx.core.Future
import io.vertx.groovy.core.Vertx

class Bye {

    Vertx vertx

    Bye(Vertx vertx) {
        this.vertx = vertx
    }

    def sayBye(String name, Closure successFn) {
        def message = "Bye, $name".toString()
        vertx.eventBus().send(Channel.BYE.name(), message, { Future reply ->
            if (reply.succeeded()) {
                println 'Success'
                successFn()
            } else {
                println reply.cause().message
            }
        })
    }

    static Bye create(Vertx vertx) {
        new Bye(vertx)
    }

}
