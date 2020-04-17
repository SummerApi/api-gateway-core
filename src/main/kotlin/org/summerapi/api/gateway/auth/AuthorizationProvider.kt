package org.summerapi.api.gateway.auth

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerRequest

object AuthorizationProvider {
  private var authorizers = mutableListOf<Authorizer>();

  fun authorizeByRequest(request: HttpServerRequest, handler: (AsyncResult<AuthorizationResult>) -> Unit) {
    var authorizer = authorizers.filter {authorizer -> authorizer.supported(request) }.firstOrNull()

    if(authorizer == null) {
      handler.invoke(Future.factory.failedFuture(IllegalArgumentException("Authorization not found with o passed args")))
    } else {
      authorizer.isAuthencated(request, handler)
    }

  }

  fun addAuthorzer(authorizer: Authorizer) {
    authorizers.add(authorizer)
  }

}
