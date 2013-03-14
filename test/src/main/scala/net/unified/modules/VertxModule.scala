package net.unified.modules

import com.google.inject.AbstractModule
import org.vertx.java.core.Vertx

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 7:51 PM
 */

object VertxModule extends AbstractModule {

  def configure() {
    bind(classOf[Vertx]).toInstance(Vertx.newVertx())
  }
}
