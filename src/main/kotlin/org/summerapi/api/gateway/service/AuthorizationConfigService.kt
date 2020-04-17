package org.summerapi.api.gateway.service

import io.vertx.core.http.HttpServerRequest

object AuthorizationConfigService {

  fun isUnsafePath(request: HttpServerRequest): Boolean {
    return false
  }

}
