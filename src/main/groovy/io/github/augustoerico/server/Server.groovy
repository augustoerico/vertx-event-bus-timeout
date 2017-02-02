package io.github.augustoerico.server

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx

class Server extends AbstractVerticle {

    void start(Future future) {

        vertx.deployVerticle()

    }

}
