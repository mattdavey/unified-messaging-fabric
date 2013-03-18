package net.unified

import com.google.inject.{Key, Injector, Guice}
import modules._
import zoo.ZooServer
import org.vertx.java.core.http.HttpServer
import com.google.inject.name.Names

object TestApp extends App {

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
    pricingHandler.start(LineHandlerConfig("FXall", List("USD/TRY", "EUR/TRY", "USD/ZAR", "EUR/ZAR", "USD/CNY", "USD/INR", "USD/MXN", "USD/SGD")))
    pricingHandler.start(LineHandlerConfig("Currenex", List("GBP/CAD", "GBP/CHF", "GBP/JPY", "GBP/NZD", "GBP/AUD", "CAD/JPY", "AUD/JPY", "AUD/CAD", "AUD/NZD", "AUD/CHF", "NZD/JPY", "CHF/JPY")))
    pricingHandler.start(LineHandlerConfig("Bloomberg", List("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CHF", "USD/CAD", "EUR/CHF", "EUR/JPY", "EUR/GBP", "EUR/CAD", "EUR/AUD", "EUR/NZD")))

    println("[Enter] to exit...")
    readLine()
  }
}
