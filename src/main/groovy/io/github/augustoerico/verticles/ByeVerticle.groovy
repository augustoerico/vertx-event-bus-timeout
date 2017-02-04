package io.github.augustoerico.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

import java.text.SimpleDateFormat

class ByeVerticle extends AbstractVerticle {

    void start(Future future) {
        println 'Starting Bye Verticle'

        def gVertx = new Vertx(vertx)
        def threadId = Thread.currentThread().id
        println "ByeVerticle.start: $threadId"

        registerConsumer(gVertx, Channel.BYE.name(), byeHandler)

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def byeHandler = { Message message ->
        def name = message.body()
        def now = new SimpleDateFormat().format(new Date())
        def threadId = Thread.currentThread().id
        println "ByeVerticle.byeHandler: $threadId"
        message.reply("Bye, $name at $now".toString())
    }

}
