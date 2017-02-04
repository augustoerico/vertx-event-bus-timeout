package io.github.augustoerico.handlers

import io.github.augustoerico.services.Bye
import io.vertx.core.Handler
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.eventbus.Message
import io.vertx.groovy.core.http.HttpServerResponse
import io.vertx.groovy.ext.web.RoutingContext

class ByeHandler {

    Vertx vertx
    String name

    static ByeHandler create(Vertx vertx, String name) {
        new ByeHandler(vertx, name)
    }

    ByeHandler(Vertx vertx, String name) {
        this.vertx = vertx
        this.name = name
    }

    def handle = { RoutingContext context ->
        def response = context.response()
        response.putHeader('content-type', 'text/plain')

        Bye.create(vertx).sayBye name,
                handleResult.curry(response),
                handleError.curry(response)

    } as Handler

    def handleResult = { HttpServerResponse response, Message message ->
        response.end(message.body())
    }

    def handleError = { HttpServerResponse response, Message message ->
        response.setStatusCode(503).end(message.body())
    }
}
