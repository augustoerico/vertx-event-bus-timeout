package io.github.augustoerico.server

import io.github.augustoerico.api.Bye
import io.github.augustoerico.api.Hello
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
                def response = context.response()
                response.putHeader('content-type', 'text/plain')

                Hello.create(vertx).sayHello('Erico', { response.end('Say hello!') })
            })

            router.route('/bye').handler({ RoutingContext context ->
                def response = context.response()
                response.putHeader('content-type', 'text/plain')

                Bye.create(vertx).sayBye('Erico', { response.end('Say bye!') })
            })

            server.requestHandler(router.&accept).listen(8080)
        })
    }

}
