package org.summerapi.api.gateway.handler

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import org.summerapi.api.gateway.auth.AuthorizationProvider
import org.summerapi.api.gateway.auth.AuthorizationResult
import org.summerapi.api.gateway.model.Config
import org.summerapi.api.gateway.model.hasPathAndMethod
import java.util.Objects

class AuthorizationHandler(private val config: Config): Handler<RoutingContext> {

  override fun handle(event: RoutingContext) {
    if(config.app.publicRouters.hasPathAndMethod(event.request().path(), event.request().rawMethod())) {
      attemptAuthorize(event)

    } else {
      followWithoutAuthorize(event)

    }

  }

  private fun attemptAuthorize(event: RoutingContext) {
    AuthorizationProvider.authorizeByRequest(event.request()) { resultFromAuthorizer ->
      when(resultFromAuthorizer.succeeded()) {
        true -> successOnAuthorize(event, resultFromAuthorizer.result())
        false -> failOnAuthorize(event, resultFromAuthorizer.cause())
      }
    }
  }

  private fun followWithoutAuthorize(context: RoutingContext): Unit {
    context.next()
  }

  private fun successOnAuthorize(context: RoutingContext, authResult: AuthorizationResult): Unit {
    val resultNonNullAndAutheticated = Objects.nonNull(authResult) && authResult.isAuthenticated();
    if(resultNonNullAndAutheticated) {
      context.next();
    } else {
      context.response().setStatusCode(401);
      context.response().setStatusMessage("Unauthorized");
      context.response().end();
    }
  }

  private fun failOnAuthorize(context: RoutingContext, throwable: Throwable): Unit {
    // a identidade não pode ser processada
    //504 Gateway Timeout
    //500 Internal Server Error
    //
  }

}


