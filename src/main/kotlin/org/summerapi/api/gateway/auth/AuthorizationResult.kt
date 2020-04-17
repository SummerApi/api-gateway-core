package org.summerapi.api.gateway.auth

interface AuthorizationResult {

  fun isAuthenticated(): Boolean

  fun getNewHeaders(): List<NewHeader>

  fun addHeader(key: String, value: String)
  fun addHeader(newHeader: NewHeader)

  fun addContextData(string: String, obj: Object)
}
