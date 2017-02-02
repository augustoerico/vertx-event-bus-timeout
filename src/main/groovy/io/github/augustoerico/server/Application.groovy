package io.github.augustoerico.server

import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.http.HttpServer
import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.ext.web.RoutingContext

class Application {

    static main(args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(ServerVerticle.name, {
            println 'Server verticle deployed'

            HttpServer server = vertx.createHttpServer()

            Router router = Router.router(vertx)

            router.route('/hello').handler({ RoutingContext context ->
                // This handler gets called for each request that arrives on the server
                def response = context.response()
                response.putHeader('content-type', 'text/plain')

                // Write to the response and end it
                response.end('Hello!')
            })

            router.route('/bye').handler({ RoutingContext context ->

                def response = context.response()
                response.putHeader('content-type', 'text/plain')

                response.end('Bye!')
            })

            server.requestHandler(router.&accept).listen(8080)
        })
    }

}
