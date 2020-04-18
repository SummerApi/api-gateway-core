package org.summerapi.api.gateway.model

class Router() {
  lateinit var service: String
  lateinit var version: String
  lateinit var nodes: List<NodeTarget>
}
