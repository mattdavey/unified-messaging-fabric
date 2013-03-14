package net.unified.messaging

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 11:23 AM
 */

trait Publisher {

  def publish[T](topic: String, payload: T)
}
