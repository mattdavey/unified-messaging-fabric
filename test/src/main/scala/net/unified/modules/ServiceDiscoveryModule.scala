package net.unified.modules

import com.google.inject.{Inject, Provider, TypeLiteral, AbstractModule}
import com.netflix.curator.x.discovery.ServiceDiscovery
import net.unified.api.ServiceInfo
import net.unified.discovery.UmfServiceDiscoveryBuilder
import com.netflix.curator.x.discovery.details.InstanceSerializer
import net.unified.json.JsonServiceInfoSerializer

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 6:50 PM
 */

object ServiceDiscoveryModule extends AbstractModule {

  private type Payload = ServiceInfo

  def configure() {
    bind(new TypeLiteral[InstanceSerializer[ServiceInfo]]() {}).toInstance(new JsonServiceInfoSerializer)
    bind(new TypeLiteral[ServiceDiscovery[Payload]]() {}).toProvider(classOf[ServiceDiscoveryProvider])
  }

  private class ServiceDiscoveryProvider @Inject()(builder: UmfServiceDiscoveryBuilder) extends Provider[ServiceDiscovery[Payload]] {

    def get(): ServiceDiscovery[Payload] = {

      val discovery = builder.build()
      discovery.start()
      discovery
    }
  }

}
