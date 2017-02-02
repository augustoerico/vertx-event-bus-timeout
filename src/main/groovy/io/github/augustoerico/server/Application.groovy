package io.github.augustoerico.server

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer

class Application {

    static main(args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(ServerVerticle.name, {
            println 'Server verticle deployed'

            HttpServer server = vertx.createHttpServer()

            server.requestHandler({ request ->
                // This handler gets called for each request that arrives on the server
                def response = request.response()
                response.putHeader('content-type', 'text/plain')

                // Write to the response and end it
                response.end('Hello World!')
            })

            server.listen(8080)
        })
    }

}
