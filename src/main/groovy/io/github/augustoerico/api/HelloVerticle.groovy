package io.github.augustoerico.api

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Future
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

class HelloVerticle extends AbstractVerticle {

    def consumers

    void start(Future<Void> future) {
        println 'Starting hello Verticle'

        def gVertx = new Vertx(vertx)

        consumers = [
                registerConsumer(gVertx, Channel.HELLO.name(), helloHandler)
        ]

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def helloHandler = { Message message ->
        println message.body()
    }

}
