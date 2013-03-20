package net.unified.zoo

import java.io.File
import org.apache.zookeeper.server.{ServerCnxnFactory, ZooKeeperServer}
import java.net.InetSocketAddress
import com.google.inject.Inject
import com.google.inject.name.Named
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 9:15 PM
 */

class ZooServer @Inject()(@Named("zoo-bind") bindAddress: InetSocketAddress) {

  private val logger = LoggerFactory.getLogger(classOf[ZooServer])
  private val numConnections = 5000
  private val tickTime = 2000


  val dataDirectory = System.getProperty("java.io.tmpdir")

  val dir = new File(dataDirectory, "zookeeper").getAbsoluteFile

  val zkServer: ZooKeeperServer = new ZooKeeperServer(dir, dir, tickTime)

  val connectionFactory = ServerCnxnFactory.createFactory
  connectionFactory.configure(bindAddress, numConnections)

  def startup() {
    logger.info("Starting embedded ZooKeeper server on port {}", bindAddress.getPort)
    connectionFactory.startup(zkServer)
  }

  def shutdown() {
    zkServer.shutdown()
    connectionFactory.shutdown()
  }
}
