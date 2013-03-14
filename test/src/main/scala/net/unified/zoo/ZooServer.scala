package net.unified.zoo

import java.io.File
import org.apache.zookeeper.server.{ServerCnxnFactory, ZooKeeperServer}
import java.net.InetSocketAddress
import com.google.inject.Inject
import com.google.inject.name.Named

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 9:15 PM
 */

class ZooServer @Inject()(@Named("zoo-bind") bindAddress: InetSocketAddress) {

  val numConnections = 5000
  val tickTime = 2000


  val dataDirectory = System.getProperty("java.io.tmpdir")

  val dir = new File(dataDirectory, "zookeeper").getAbsoluteFile()

  val zkServer: ZooKeeperServer = new ZooKeeperServer(dir, dir, tickTime)

  val cnxnFactory = ServerCnxnFactory.createFactory
  cnxnFactory.configure(bindAddress, numConnections)

  def startup() {
    cnxnFactory.startup(zkServer)
  }

  def shutdown() {
    zkServer.shutdown()
    cnxnFactory.shutdown()
  }
}
