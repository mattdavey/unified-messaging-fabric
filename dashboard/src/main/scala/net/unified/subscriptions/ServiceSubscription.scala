package net.unified.subscriptions

import org.vertx.java.core.Handler
import org.vertx.java.core.sockjs.SockJSSocket
import org.vertx.java.core.buffer.Buffer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import net.unified.api.messaging.Consumer
import rx.{Subscription, Observer}
import com.google.inject.Inject
import net.unified.subscriptions.ServiceSubscription.Update

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 5:41 PM
 */

object ServiceSubscription {

  case class Request(service: String, topic: String, sub: Boolean)

  case class Update(service: String, topic: String, value: Any)

}

class ServiceSubscription @Inject()(consumer: Consumer) extends Handler[SockJSSocket] {

  private var subscriptions = Map.empty[String, Subscription]
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def handle(socket: SockJSSocket) {

    socket.dataHandler(new DataHandler(socket))
    socket.endHandler(new EndHandler())
  }

  private class DataHandler(socket: SockJSSocket) extends Handler[Buffer] {

    def handle(event: Buffer) {
      val ServiceSubscription.Request(service, topic, subscribe) = mapper.readValue(event.getBytes, classOf[ServiceSubscription.Request])
      val umfTopic = service + "." + topic

      subscribe match {
        case true => {
          val subscription = consumer.subscribe(umfTopic, new TopicPublisher(service, topic))
          subscriptions = subscriptions updated(umfTopic, subscription)
        }
        case false => {
          subscriptions.get(umfTopic).foreach(_.unsubscribe())
          subscriptions = subscriptions - umfTopic
        }
      }
    }

    private class TopicPublisher(service: String, topic: String) extends Observer[Any] {
      def onCompleted() {}

      def onError(e: Exception) {}

      def onNext(args: Any) {
        val bytes = mapper.writeValueAsBytes(Update(service, topic, args))
        socket.writeBuffer(new Buffer(bytes))
      }
    }

  }

  private class EndHandler() extends Handler[Void] {
    def handle(event: Void) {
      subscriptions.values.foreach(_.unsubscribe())
      subscriptions = Map.empty[String, Subscription]
    }
  }

}
