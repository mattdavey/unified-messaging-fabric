package net.unified.rest

import net.unified.rest.SubscriptionListHandler.{Id, ServiceTopic}
import java.util.UUID

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/15/13
 * Time: 7:57 PM
 */

object SubscriptionListHandler {

  case class ServiceTopic(id: String, service: String, topic: String)

  case class Id(id: String)

}

class SubscriptionListHandler {

  private var subscriptions: Map[String, ServiceTopic] = Map()

  def get(): Option[Iterable[ServiceTopic]] = Some(subscriptions.values)

  def post(subscription: ServiceTopic): Id = {
    val id = UUID.randomUUID().toString
    subscriptions = subscriptions + (id -> subscription.copy(id = id))
    Id(id)
  }

  def del(id: String) {
    subscriptions = subscriptions - id
  }
}
