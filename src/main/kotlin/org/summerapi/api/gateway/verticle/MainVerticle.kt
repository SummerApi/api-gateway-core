package org.summerapi.api.gateway.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise

class MainVerticle: AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    vertx.deployVerticle(ServerVerticle("app.json"))
  }

}
