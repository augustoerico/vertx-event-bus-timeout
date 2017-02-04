package io.github.augustoerico.handlers

import io.github.augustoerico.services.Hello
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message
import io.vertx.groovy.core.http.HttpServerResponse
import io.vertx.groovy.ext.web.RoutingContext

class HelloHandler {

    Vertx vertx
    String name

    static HelloHandler create(Vertx vertx, String name) {
        new HelloHandler(vertx, name)
    }

    HelloHandler(Vertx vertx, String name) {
        this.vertx = vertx
        this.name = name
    }

    def handle = { RoutingContext context ->
        // Same as Application.main thread
        def thread = Thread.currentThread()
        println "HelloHandler.handle: thread: $thread.id | $thread.name"

        def response = context.response()
        response.putHeader('content-type', 'text/plain')

        Hello.create(vertx).sayHello name,
                handleResult.curry(response),
                handleError.curry(response)

    } as Handler

    def handleResult = { HttpServerResponse response, Message message ->
        // Same as Application.main thread
        def thread = Thread.currentThread()
        println "HelloHandler.handleResult: thread: $thread.id | $thread.name"
        response.end(message.body())
    }

    def handleError = { HttpServerResponse response, Message message ->
        // Same as Application.main thread
        def thread = Thread.currentThread()
        println "HelloHandler.handleError: thread: $thread.id | $thread.name"
        response.setStatusCode(503).end(message)
    }

}
