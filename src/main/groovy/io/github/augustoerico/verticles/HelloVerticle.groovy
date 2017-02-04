package io.github.augustoerico.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

import java.text.SimpleDateFormat

class HelloVerticle extends AbstractVerticle {

    Thread thread

    void start(Future<Void> future) {
        def thread = Thread.currentThread()
        println "HelloVerticle.start: $thread.id | $thread.name"

        registerConsumer(
                new Vertx(vertx),
                Channel.HELLO.name(),
                helloHandler
        )

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def helloHandler = { Message message ->
        // Thread from worker pool (worker verticle)
        this.thread = Thread.currentThread()
        println "HelloVerticle.helloHandler: $thread.id"

        def name = message.body()
        def now = new SimpleDateFormat().format(new Date())

        // Let's pretend it is a resource hunger operation that takes up to 1s
        Thread.sleep(new Random().nextInt(10) * 100)

        message.reply("Hello, $name at $now".toString())
    }

}
