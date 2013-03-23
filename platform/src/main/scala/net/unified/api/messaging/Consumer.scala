package net.unified.api.messaging

import rx.{Observer, Subscription}

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 11:24 AM
 */

trait Consumer {
  def subscribe[T](topic: String, observer: Observer[T]): Subscription
}
