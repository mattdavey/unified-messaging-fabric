package net.unified.modules

import com.google.inject.{Inject, Provider, TypeLiteral, AbstractModule}
import com.netflix.curator.x.discovery.{ServiceDiscoveryBuilder, ServiceDiscovery}
import com.netflix.curator.x.discovery.details.JsonInstanceSerializer
import com.netflix.curator.framework.CuratorFramework
import net.unified.api.ServiceInfo

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 6:50 PM
 */

object ServiceDiscoveryModule extends AbstractModule {

  private type Payload = ServiceInfo

  def configure() {
    bind(new TypeLiteral[ServiceDiscovery[Payload]]() {}).toProvider(classOf[ServiceDiscoveryProvider])
  }

  private class ServiceDiscoveryProvider @Inject()(client: CuratorFramework) extends Provider[ServiceDiscovery[Payload]] {

    def get(): ServiceDiscovery[ServiceInfo] = {

      val discovery = ServiceDiscoveryBuilder.builder[Payload](classOf[Payload])
        .client(client)
        .basePath("/umf-services")
        .serializer(new JsonInstanceSerializer[Payload](classOf[Payload]))
        .build()
      discovery.start()
      discovery
    }
  }

}
