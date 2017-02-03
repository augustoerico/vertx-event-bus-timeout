package io.github.augustoerico.api

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message

import java.text.SimpleDateFormat

class HelloVerticle extends AbstractVerticle {

    Thread thread

    void start(Future<Void> future) {
        println 'Starting Hello Verticle'
        def threadId = Thread.currentThread().id
        println "HelloVerticle.start: $threadId"

        def gVertx = new Vertx(vertx)

        registerConsumer(gVertx, Channel.HELLO.name(), helloHandler).exceptionHandler({
            println 'JIAJDIAJIDAISJDIASJIDASIJDAI'
        })

        future.complete()
    }

    def registerConsumer(Vertx vertx, String consumerName, Handler handler) {
        vertx.eventBus().consumer(consumerName, handler)
    }

    def helloHandler = { Message message ->
        def name = message.body()
        def now = new SimpleDateFormat().format(new Date())
        this.thread = Thread.currentThread()
        println "HelloVerticle.helloHandler: $thread.id"

        def task = new TimerTask() {
            @Override
            void run() {
                println 'Stopping thread'
                if (thread && !thread.interrupted) {
                    thread.interrupt()
                    message.fail(503, 'FAILED AS ALL YOUR DREAMS')
                }
            }
        }

        println 'Starting timer'
        Timer timer = new Timer()
        timer.schedule(task, 3000)

        try {
            def random = new Random().nextInt(10)
            if (random < 2) {
                println 'Doing intense calculation'
                Thread.sleep(65000)
            }
            println 'Cancelling timer'
            timer.cancel()
            timer.purge()
            message.reply("Hello, $name at $now".toString())
        } catch(Exception e) {
            println 'qwerqwerqwerqwerqwerqwerqw'
            e.printStackTrace()
//            message.fail(503, 'FAILED AS ALL YOUR DREAMS')
        }
    }

}
