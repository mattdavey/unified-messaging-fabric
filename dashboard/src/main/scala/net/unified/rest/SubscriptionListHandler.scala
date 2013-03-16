package net.unified.rest

import net.unified.rest.SubscriptionListHandler.ServiceTopic

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/15/13
 * Time: 7:57 PM
 */

object SubscriptionListHandler {

  case class ServiceTopic(service: String, topic: String)

}

class SubscriptionListHandler {

  private var subscriptions: Set[ServiceTopic] = Set(ServiceTopic("360T", "USD/JPY"))

  def get() = Some(subscriptions)

  def put(subscription: ServiceTopic) {
    subscriptions = subscriptions + subscription
  }

  def del(subscription: ServiceTopic) {
    subscriptions = subscriptions - subscription
  }
}
