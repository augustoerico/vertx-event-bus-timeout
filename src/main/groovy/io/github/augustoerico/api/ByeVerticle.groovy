package io.github.augustoerico.api

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

class ByeVerticle extends AbstractVerticle {

    void start(Future future) {
        println 'Starting Bye Verticle'

        def gVertx = new Vertx(vertx)

        registerConsumer(gVertx, Channel.BYE.name(), byeHandler)

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def byeHandler = { Message message ->
        println message.body()
        message.reply('Done')
    }

}
