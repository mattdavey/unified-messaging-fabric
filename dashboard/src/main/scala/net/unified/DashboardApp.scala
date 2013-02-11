package net.unified

import org.atmosphere.nettosphere.{Nettosphere, Config}
import org.slf4j.LoggerFactory

object DashboardApp extends App {

  private val logger = LoggerFactory.getLogger(getClass)

  val configBuilder = new Config.Builder()
  configBuilder.resource("./webapps")
    .port(8080)
    .host("127.0.0.1")
    .build()

  val nettosphere = new Nettosphere.Builder().config(configBuilder.build()).build()
  nettosphere.start()

  logger.info("Nettosphere Dashboard Server started on port {}", 8080)
}
