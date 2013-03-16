package net.unified.rest

import com.google.inject.Inject
import com.netflix.curator.x.discovery.ServiceDiscovery
import net.unified.api.ServiceInfo
import collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 9:10 PM
 */


class ServiceDetailsHandler @Inject()(discovery: ServiceDiscovery[ServiceInfo]) {

  def get(id: String): Option[ServiceInfo] = {

    val cache = discovery.serviceCacheBuilder().name("umf-service").build()
    cache.start()
    val list = cache.getInstances
    val instance = list.find(_.getId == id)
    instance.map(_.getPayload)
  }
}
