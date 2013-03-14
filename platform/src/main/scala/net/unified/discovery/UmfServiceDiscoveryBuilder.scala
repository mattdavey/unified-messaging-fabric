package net.unified.discovery

import com.netflix.curator.x.discovery.ServiceDiscoveryBuilder
import net.unified.api.ServiceInfo
import com.google.inject.Inject
import com.netflix.curator.framework.CuratorFramework
import com.netflix.curator.x.discovery.details.InstanceSerializer

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 5:35 PM
 */

class UmfServiceDiscoveryBuilder @Inject()(client: CuratorFramework,
                                           serializer: InstanceSerializer[ServiceInfo]) {

  private val builder = ServiceDiscoveryBuilder
    .builder[ServiceInfo](classOf[ServiceInfo])
    .client(client)
    .basePath("/umf-services")
    .serializer(serializer)

  def build() = builder.build()
}
