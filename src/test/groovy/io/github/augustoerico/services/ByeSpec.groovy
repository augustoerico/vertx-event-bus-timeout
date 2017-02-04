package io.github.augustoerico.services

import io.github.augustoerico.verticles.ByeVerticle
import io.vertx.groovy.core.Vertx
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions
import spock.util.concurrent.BlockingVariables

import java.util.concurrent.TimeUnit

class ByeSpec extends Specification {

    @Shared
    Vertx vertx

    def setupSpec() {

        def cons = new AsyncConditions()

        vertx = Vertx.vertx()

        vertx.deployVerticle(ByeVerticle.name) {
            println 'Hello verticle deployed'
            cons.evaluate { true }
        }

        cons.await(5.0)
    }

    def 'Should say hello'() {
        def vars = new BlockingVariables(5, TimeUnit.SECONDS)

        given:
        def successFn = { vars.success = true }

        when:
        Bye.create(vertx).sayBye('Erico', successFn)

        then:
        vars.success

    }

}
