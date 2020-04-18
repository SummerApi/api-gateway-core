package org.summerapi.api.gateway.model

class App() {
  lateinit var server: Server
  lateinit var routers: List<Router>
  lateinit var publicRouters: List<PublicRouter>
}
