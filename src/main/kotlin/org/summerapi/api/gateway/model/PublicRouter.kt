package org.summerapi.api.gateway.model

class PublicRouter() {
  lateinit var path: String
  lateinit var method: String
}

fun  List<PublicRouter>.hasPathAndMethod(path: String?, method: String?): Boolean {
  return filter { pr -> pr.path.equals(path) && pr.method.equals(method) }.isEmpty()
}
