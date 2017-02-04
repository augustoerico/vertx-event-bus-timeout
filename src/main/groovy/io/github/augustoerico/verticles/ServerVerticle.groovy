package io.github.augustoerico.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.groovy.core.Vertx

class ServerVerticle extends AbstractVerticle {

    void start(Future future) {
        def thread = Thread.currentThread()

        println "ServerVerticle.start: $thread.id"
        Vertx gVertx = new Vertx(vertx)

        gVertx.deployVerticle(HelloVerticle.name, [worker: true], {
            println 'ServerVerticle.start: HelloVerticle deployed'
            gVertx.deployVerticle(ByeVerticle.name, {
                println 'ServerVerticle.start: ByeVerticle deployed'
                future.complete()
            })
        })
    }

}
