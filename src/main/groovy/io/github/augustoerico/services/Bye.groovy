package io.github.augustoerico.services

import io.github.augustoerico.verticles.Channel
import io.vertx.core.Future
import io.vertx.groovy.core.Vertx

class Bye {

    Vertx vertx

    Bye(Vertx vertx) {
        this.vertx = vertx
    }

    def sayBye(String name, Closure successFn, Closure failureFn) {
        vertx.eventBus().send(
                Channel.BYE.name(),
                name,
                { Future reply ->

                    def thread = Thread.currentThread()
                    println 'Bye.sayBye: handling reply'
                    println "Bye.sayBye: thread: $thread.id | $thread.name"

                    if (reply.succeeded()) {
                        def result = reply.result()
                        println 'Bye.sayBye: success'
                        successFn(result)
                    } else {
                        def message = reply.cause().message
                        println "Bye.sayBye: failed: $message"
                        failureFn(message)
                    }

                }
        )
    }

    static Bye create(Vertx vertx) {
        new Bye(vertx)
    }

}
