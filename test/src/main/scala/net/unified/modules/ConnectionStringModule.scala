package net.unified.modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import java.net.InetSocketAddress

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 6:30 PM
 */

object ConnectionStringModule extends AbstractModule {

  def configure() {
    bind(classOf[InetSocketAddress]).annotatedWith(Names.named("zoo-bind")).toInstance(new InetSocketAddress(2181))
    bind(classOf[String]).annotatedWith(Names.named("zoo-connect")).toInstance("127.0.0.1:2181")
  }
}
