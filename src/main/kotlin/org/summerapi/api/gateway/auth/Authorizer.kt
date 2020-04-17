package org.summerapi.api.gateway.auth

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerRequest

interface Authorizer: AuthorizerSupport {
  fun isAuthencated(request: HttpServerRequest, resultHandler: (AsyncResult<AuthorizationResult>) -> Unit): Boolean
}
