package io.github.augustoerico.api

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Future
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

import java.text.SimpleDateFormat

class HelloVerticle extends AbstractVerticle {

    void start(Future<Void> future) {
        println 'Starting Hello Verticle'
        def threadId = Thread.currentThread().id
        println "HelloVerticle.start: $threadId"

        def gVertx = new Vertx(vertx)

        registerConsumer(gVertx, Channel.HELLO.name(), helloHandler)

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def helloHandler = { Message message ->
        def name = message.body()
        def now = new SimpleDateFormat().format(new Date())
        def threadId = Thread.currentThread().id
        println "HelloVerticle.helloHandler: $threadId"
        def random = new Random().nextInt(10)
        if (random < 2) {
            println 'Doing intense calculation'
            Thread.sleep(180000)
        }
        message.reply("Hello, $name at $now".toString())
    }

}
