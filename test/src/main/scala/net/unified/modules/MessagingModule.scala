package net.unified.modules

import com.google.inject.AbstractModule
import net.unified.messaging.{Consumer, Publisher}
import net.unified.mock.InMemoryMessageBus

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 12:43 PM
 */

object MessagingModule extends AbstractModule {

  private val bus = new InMemoryMessageBus

  def configure() {
    bind(classOf[Publisher]).toInstance(bus)
    bind(classOf[Consumer]).toInstance(bus)
  }
}
