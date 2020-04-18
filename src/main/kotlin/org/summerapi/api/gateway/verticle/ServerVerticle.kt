package org.summerapi.api.gateway.verticle

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.summerapi.api.gateway.handler.AuthorizationHandler
import org.summerapi.api.gateway.handler.ExchangeHandler
import org.summerapi.api.gateway.handler.RateLimitHandler
import org.summerapi.api.gateway.model.Config


class ServerVerticle(private val pathToConfigFile: String): AbstractVerticle() {



  override fun start(startPromise: Promise<Void>) {
    //AuthorizationHandler()
    //RateLimitHandler()
    //CacheHandler()
    //ExchangeHandler()
    //TriggerHandler()


    val configStoreOptions = ConfigStoreOptions()
    configStoreOptions.type = "file"
    configStoreOptions.config = json {
      obj("path" to pathToConfigFile)
    }

    val configRetrieverOptions = ConfigRetrieverOptions ()
      .addStore(configStoreOptions)

    ConfigRetriever.create(vertx, configRetrieverOptions).getConfig { config ->
      if(config.failed()) {

      } else {
        val config = config.result().mapTo(Config::class.java)

        val router: Router = Router.router(vertx)
        router.route().handler(AuthorizationHandler(config))
        router.route().handler(RateLimitHandler())
        router.route().handler(ExchangeHandler())

        val httpServerOptions = HttpServerOptions()
          .setTcpFastOpen(true)
          .setTcpNoDelay(true)
          .setTcpQuickAck(true)
          .setPort(config.app.server.port.toInt())

        val httpServer: HttpServer = vertx.createHttpServer(httpServerOptions)
        httpServer.requestHandler(router).listen(httpServerOptions.port, { http ->
          if (http.succeeded()) {
            System.out.println("[Proxy] Servidor iniciado na porta " + config.app.server.port)
            startPromise.complete()
          } else {
            startPromise.fail(http.cause())
          }
        })

      }
    }
  }

}
