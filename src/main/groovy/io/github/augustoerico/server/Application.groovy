package io.github.augustoerico.server

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer

class Application {

    static main(args) {

        Vertx vertx = Vertx.vertx()

        vertx.deployVerticle()

        HttpServer server = vertx.createHttpServer()

        server.listen(5000)

    }

}
