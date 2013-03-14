package net.unified

import com.google.inject.{Key, Injector, Guice}
import modules._
import zoo.ZooServer
import org.vertx.java.core.http.HttpServer
import com.google.inject.name.Names
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object TestApp extends App {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  val injector = Guice.createInjector(
    ConnectionStringModule,
    ZooKeeperModule,
    VertxModule,
    ServiceDiscoveryModule,
    DashboardModule,
    PricingLineHandlerModule,
    MessagingModule)

  val zoo = injector.getInstance(classOf[ZooServer])
  zoo.startup()

  val app = new TestApp
  app.run(injector)

  zoo.shutdown()
}


class TestApp {

  def run(injector: Injector) {

    // Start dashboard web and push server, available at http://localhost:8080
    val dashboardWeb = injector.getInstance(Key.get(classOf[HttpServer], Names.named("dashboard")))
    dashboardWeb.listen(8080)

    // Start pricing line handler simulator(s)
    val pricingHandler = injector.getInstance(classOf[PricingService])
    pricingHandler.start(LineHandlerConfig("360T", List("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CHF", "USD/CAD")))

    println("[Enter] to exit...")
    readLine()
  }
}
