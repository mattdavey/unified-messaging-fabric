package net.unified

import api.ServiceInfo
import org.slf4j.LoggerFactory
import org.apache.zookeeper.KeeperException.ConnectionLossException
import com.netflix.curator.x.discovery.{ServiceDiscoveryBuilder, ServiceInstance}
import com.netflix.curator.framework.CuratorFrameworkFactory
import com.netflix.curator.retry.ExponentialBackoffRetry
import com.netflix.curator.x.discovery.details.JsonInstanceSerializer


object ServiceApp {
  val logger = LoggerFactory.getLogger(getClass)
  val usage = """
    Usage: --service-name <string> [--zoo-connect <string>] [--zoo-timeout <ms>]
              """

  def main(args: Array[String]) {

    type Payload = ServiceInfo

    if (args.length == 0) println(usage)
    else {
      try {
        val config = nextOption(Config(), args.toList)

        val service = config.serviceName.getOrElse(throw new ConfigException("service-name is required"))
        logger.info(s"Registering service: $service...")

        val serviceInstance = ServiceInstance.builder[Payload]().name(service).build()
        val retryPolicy = new ExponentialBackoffRetry(1000, 3)
        val zooClient = CuratorFrameworkFactory.newClient(config.zooConnectionString, retryPolicy)
        zooClient.start()
        val discovery = ServiceDiscoveryBuilder.builder[Payload](classOf[Payload])
          .client(zooClient)
          .basePath("/umf-services")
          .serializer(new JsonInstanceSerializer[Payload](classOf[Payload]))
          .thisInstance(serviceInstance)
          .build()
        discovery.start()

        logger.info(s"Service registered: $service.")
        println("[Enter] to exit...")
        readLine()
      }
      catch {
        case x: ConfigException => {
          logger.error(x.getMessage, x)
          println(usage)
        }
        case x: ConnectionLossException => {
          logger.error(x.getMessage, x)
        }
      }
    }
  }

  private class ConfigException(message: String) extends RuntimeException(message)

  private case class Config(zooConnectionString: String = "127.0.0.1:2181",
                            zooSessionTimeout: Int = 3000,
                            serviceName: Option[String] = None)

  private def nextOption(config: Config, list: List[String]): Config = {
    list match {
      case Nil => config
      case "--service-name" :: value :: tail => nextOption(config.copy(serviceName = Some(value)), tail)
      case "--zoo-connect" :: value :: tail => nextOption(config.copy(zooConnectionString = value), tail)
      case "--zoo-timeout" :: value :: tail => nextOption(config.copy(zooSessionTimeout = value.toInt), tail)
      case option :: tail =>
        println("Unknown option " + option)
        sys.exit(1)
    }
  }

}
