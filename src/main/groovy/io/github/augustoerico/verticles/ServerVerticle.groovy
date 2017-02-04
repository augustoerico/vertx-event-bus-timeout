package io.github.augustoerico.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.groovy.core.Vertx

class ServerVerticle extends AbstractVerticle {

    void start(Future future) {
        def threadId = Thread.currentThread().id
        println "ServerVerticle.start: $threadId"
        Vertx gVertx = new Vertx(vertx)
        gVertx.deployVerticle(HelloVerticle.name, [worker: true], {
            gVertx.deployVerticle(ByeVerticle.name, {
                println 'Verticles deployed'
                future.complete()
            })
        })
    }

}
