package net.unified.modules

import com.google.inject.{TypeLiteral, AbstractModule}
import com.netflix.curator.x.discovery.details.InstanceSerializer
import net.unified.json.JsonServiceInfoSerializer
import net.unified.api.discovery.ServiceInfo

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
  }
}
