package net.unified.subscriptions

import org.vertx.java.core.Handler
import org.vertx.java.core.sockjs.SockJSSocket
import com.netflix.curator.x.discovery.ServiceInstance
import org.vertx.java.core.buffer.Buffer
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import net.unified.discovery.UmfServiceDiscoveryBuilder
import net.unified.api.discovery.ServiceInfo

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 12:52 PM
 */

class ServiceCacheSubscription @Inject()(builder: UmfServiceDiscoveryBuilder) extends Handler[SockJSSocket] {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def handle(socket: SockJSSocket) {

    monitorServices(socket)
  }

  private def monitorServices(socket: SockJSSocket) {

    val discovery = builder.buildDiscovery()
    sendSnapshot(socket, discovery.snapshot())
    discovery.subscribe(new discovery.Sub {
      def notify(pub: discovery.Pub, event: Unit) {
        sendSnapshot(socket, discovery.snapshot())
      }
    })
  }

  private def sendSnapshot(socket: SockJSSocket, snapshot: List[ServiceInstance[ServiceInfo]]) {

    val event = Event("snapshot", snapshot)
    val bytes = mapper.writeValueAsBytes(event)
    socket.writeBuffer(new Buffer(bytes))
  }
}
