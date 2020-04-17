package org.summerapi.api.gateway.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import org.summerapi.api.gateway.handler.*

class ServerVerticle: AbstractVerticle() {

  override fun start(startPromise: Promise<Void>?) {
    //AuthorizationHandler()
    //RateLimitHandler()
    //CacheHandler()
    //ExchangeHandler()
    //TriggerHandler()
  }

}
