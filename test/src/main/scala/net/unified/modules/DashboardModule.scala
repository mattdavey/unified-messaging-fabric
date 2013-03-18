package net.unified.modules

import com.google.inject.{Scopes, Provider, Inject, AbstractModule}
import org.vertx.java.core.http.HttpServer
import org.vertx.java.core.Vertx
import com.google.inject.name.Names
import org.vertx.java.core.json.JsonObject
import net.unified.subscriptions.{ServiceSubscription, ServiceCacheSubscription}
import net.unified.DashboardWebServer

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 7:46 PM
 */

object DashboardModule extends AbstractModule {

  def configure() {

    bind(classOf[HttpServer]).annotatedWith(Names.named("dashboard")).toProvider(classOf[DashboardWebServerProvider]).in(Scopes.SINGLETON)
  }


  private class DashboardWebServerProvider @Inject()(vertx: Vertx,
                                                     webServerProvider: DashboardWebServer,
                                                     services: ServiceCacheSubscription,
                                                     topics: ServiceSubscription) extends Provider[HttpServer] {
    def get(): HttpServer = {

      val http = webServerProvider.create(vertx)
      val socket = vertx.createSockJSServer(http)
      socket.installApp(new JsonObject().putString("prefix", "/realtime/services"), services)
      socket.installApp(new JsonObject().putString("prefix", "/realtime/data"), topics)

      http
    }
  }

}
