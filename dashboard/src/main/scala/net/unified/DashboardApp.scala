package net.unified

import api.ServiceInfo
import org.slf4j.LoggerFactory
import org.vertx.java.core.Vertx
import org.vertx.java.core.json.JsonObject
import com.netflix.curator.x.discovery.ServiceDiscoveryBuilder
import com.netflix.curator.x.discovery.details.JsonInstanceSerializer
import com.netflix.curator.retry.ExponentialBackoffRetry
import com.netflix.curator.framework.CuratorFrameworkFactory
import subscriptions.ServiceCacheSubscription

object DashboardApp extends App {

  private type Payload = ServiceInfo
  private val logger = LoggerFactory.getLogger(getClass)

  private val config = nextOption(Config(), args.toList)

  val retryPolicy = new ExponentialBackoffRetry(1000, 3)
  val zooClient = CuratorFrameworkFactory.newClient(config.zooConnectionString, retryPolicy)
  zooClient.start()
  val discovery = ServiceDiscoveryBuilder.builder[Payload](classOf[Payload])
    .client(zooClient)
    .basePath("/services")
    .serializer(new JsonInstanceSerializer[Payload](classOf[Payload]))
    .build()
  discovery.start()

  val vertx = Vertx.newVertx()
  val http = DashboardWebServer.create(vertx)

  val socket = vertx.createSockJSServer(http)
  socket.installApp(new JsonObject().putString("prefix", "/services"), new ServiceCacheSubscription(discovery))


  http.listen(8080)
  logger.info("Web Server started on port {}", 8080)


  println("[Enter] to exit...")
  readLine()

  private case class Config(zooConnectionString: String = "127.0.0.1:2181",
                            zooSessionTimeout: Int = 3000)

  private def nextOption(config: Config, list: List[String]): Config = {
    list match {
      case Nil => config
      case "--zoo-connect" :: value :: tail => nextOption(config.copy(zooConnectionString = value), tail)
      case "--zoo-timeout" :: value :: tail => nextOption(config.copy(zooSessionTimeout = value.toInt), tail)
      case option :: tail =>
        println("Unknown option " + option)
        sys.exit(1)
    }
  }

}
