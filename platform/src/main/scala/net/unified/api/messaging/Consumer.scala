package net.unified.messaging

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 11:24 AM
 */

trait Consumer {
  def subscribe(topic: String, handler: Any): Unit
}
