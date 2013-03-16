package net.unified.rest

import net.unified.rest.SubscriptionListHandler.TopicSubscription

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/15/13
 * Time: 7:57 PM
 */

object SubscriptionListHandler {

  case class TopicSubscription(id: String, topic: String)

}

class SubscriptionListHandler {

  private var subscriptions: Set[TopicSubscription] = Set.empty

  def get() = Some(subscriptions)

  def put(subscription: TopicSubscription) {
    subscriptions = subscriptions + subscription
  }

  def del(subscription: TopicSubscription) {
    subscriptions = subscriptions - subscription
  }
}
