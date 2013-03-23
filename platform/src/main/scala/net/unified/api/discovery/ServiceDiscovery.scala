package net.unified.api.discovery

import collection.mutable
import com.netflix.curator.x.discovery.ServiceInstance

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/22/13
 * Time: 7:00 PM
 */

trait ServiceDiscovery extends mutable.Publisher[Unit] {

  def snapshot(): List[ServiceInstance[ServiceInfo]]
}
