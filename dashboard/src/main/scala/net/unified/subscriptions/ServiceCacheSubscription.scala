package net.unified.subscriptions

import org.vertx.java.core.Handler
import org.vertx.java.core.sockjs.SockJSSocket
import com.netflix.curator.x.discovery.ServiceDiscovery
import com.netflix.curator.x.discovery.details.ServiceCacheListener
import org.vertx.java.core.buffer.Buffer
import com.netflix.curator.framework.CuratorFramework
import com.netflix.curator.framework.state.ConnectionState
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import net.unified.api.ServiceInfo

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 12:52 PM
 */

class ServiceCacheSubscription @Inject()(discovery: ServiceDiscovery[ServiceInfo]) extends Handler[SockJSSocket] {

  val cache = discovery.serviceCacheBuilder().name("umf-service").build()
  cache.start()
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def handle(socket: SockJSSocket) {

    sendSnapshot(socket)

    cache.addListener(new ServiceCacheListener {
      def cacheChanged() {
        socket.writeBuffer(new Buffer("123"))
      }

      def stateChanged(client: CuratorFramework, newState: ConnectionState) {
      }
    })
  }

  private def sendSnapshot(socket: SockJSSocket) {
    val l = cache.getInstances
    val event = Event("snapshot", l)
    val bytes = mapper.writeValueAsBytes(event)
    socket.writeBuffer(new Buffer(bytes))
  }
}
