package net.unified.mock

import net.unified.api.messaging.{Consumer, Publisher}
import rx.{Observable, Subscription, Observer}
import rx.subjects.Subject

import collection.JavaConversions._


/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 11:38 AM
 */

class InMemoryMessageBus extends Publisher with Consumer {

  private val bus = new java.util.concurrent.ConcurrentHashMap[String, Subject[Any]]

  def publish[T](topic: String, payload: T) {
    val subject = bus.getOrElseUpdate(topic, Subject.create[Any]())
    subject.onNext(payload)
  }

  def subscribe[T](topic: String, observer: Observer[T]): Subscription = {
    val subject = bus.getOrElseUpdate(topic, Subject.create[Any]())
    Observable.map(subject, (v: Any) => v.asInstanceOf[T]).subscribe(observer)
  }
}
