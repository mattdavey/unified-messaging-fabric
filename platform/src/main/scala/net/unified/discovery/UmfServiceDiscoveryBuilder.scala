package net.unified.discovery

import com.netflix.curator.x.discovery.{ServiceCache, ServiceInstance, ServiceDiscoveryBuilder}
import com.google.inject.Inject
import com.netflix.curator.framework.CuratorFramework
import com.netflix.curator.x.discovery.details.{ServiceCacheListener, InstanceSerializer}
import net.unified.api.discovery.{ServiceInfo, ServiceRegistry, ServiceDiscovery}
import com.netflix.curator.framework.state.ConnectionState
import collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 5:35 PM
 */

class UmfServiceDiscoveryBuilder @Inject()(client: CuratorFramework,
                                           serializer: InstanceSerializer[ServiceInfo]) {

  private val serviceTypes = List("pricing", "order")
  private val builder = ServiceDiscoveryBuilder
    .builder[ServiceInfo](classOf[ServiceInfo])
    .client(client)
    .basePath("/umf-services")
    .serializer(serializer)

  def buildDiscovery(): ServiceDiscovery = new ServiceDiscoveryImpl()

  def buildRegistry(): ServiceRegistry = new ServiceRegistryImpl()

  private class ServiceRegistryImpl() extends ServiceRegistry {
    def registerService(serviceType: String, id: String, info: ServiceInfo) {
      val instance = ServiceInstance.builder[ServiceInfo]()
        .name(serviceType).id(id)
        .payload(info)
        .build()
      builder.build().registerService(instance)
    }
  }

  private class ServiceDiscoveryImpl() extends ServiceDiscovery {

    private val discovery = builder.build()
    private val caches = serviceTypes.map(discovery.serviceCacheBuilder().name(_).build())

    discovery.start()
    caches.foreach(_.start())
    caches.foreach(addListener(_))

    def snapshot(): List[ServiceInstance[ServiceInfo]] = caches.flatMap(_.getInstances.toList)

    private def addListener(cache: ServiceCache[ServiceInfo]) {
      cache.addListener(new ServiceCacheListener {
        def cacheChanged() {
          publish()
        }

        def stateChanged(client: CuratorFramework, newState: ConnectionState) {}
      })
    }
  }

}
