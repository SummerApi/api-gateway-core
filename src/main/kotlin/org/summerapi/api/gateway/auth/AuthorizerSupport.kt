package org.summerapi.api.gateway.auth

import io.vertx.core.http.HttpServerRequest

interface AuthorizerSupport {
  fun supported(request: HttpServerRequest): Boolean
}
