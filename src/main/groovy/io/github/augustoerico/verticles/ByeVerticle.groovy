package io.github.augustoerico.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

import java.text.SimpleDateFormat

class ByeVerticle extends AbstractVerticle {

    void start(Future future) {
        def thread = Thread.currentThread()
        println "ByeVerticle.start: $thread.id | $thread.name"

        registerConsumer(
                new Vertx(vertx),
                Channel.BYE.name(),
                byeHandler
        )

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def byeHandler = { Message message ->
        def threadId = Thread.currentThread().id
        println "ByeVerticle.byeHandler: $threadId"

        def name = message.body()
        def now = new SimpleDateFormat().format(new Date())

        // Let's pretend it's a resource hunger operation that takes up to 1s
        Thread.sleep(new Random().nextInt(10) * 100)

        message.reply("Bye, $name at $now".toString())
    }

}
