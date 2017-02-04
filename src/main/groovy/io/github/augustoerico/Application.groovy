package io.github.augustoerico

import io.github.augustoerico.handlers.ByeHandler
import io.github.augustoerico.handlers.HelloHandler
import io.github.augustoerico.verticles.ByeVerticle
import io.github.augustoerico.verticles.HelloVerticle
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.core.http.HttpServer
import io.vertx.groovy.ext.web.Router

class Application {

    static final String NAME = 'Erico'

    static main(args) {
        Vertx vertx = Vertx.vertx()

        vertx.deployVerticle(HelloVerticle.name, [worker: true], {
            vertx.deployVerticle(ByeVerticle.name, {

                def thread = Thread.currentThread()
                println 'All verticles deployed'
                println "Application.main: thread: $thread.id | $thread.name"

                Router router = Router.router(vertx)
                router.route('/hello').handler HelloHandler.create(vertx, NAME).handle
                router.route('/bye').handler ByeHandler.create(vertx, NAME).handle

                vertx.createHttpServer()
                        .requestHandler(router.&accept)
                        .listen(8080)

            })
        })
    }
}
