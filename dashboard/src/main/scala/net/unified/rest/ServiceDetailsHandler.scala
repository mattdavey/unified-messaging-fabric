package net.unified.rest

import com.google.inject.Inject
import net.unified.discovery.UmfServiceDiscoveryBuilder
import net.unified.api.discovery.ServiceInfo

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 9:10 PM
 */


class ServiceDetailsHandler @Inject()(builder: UmfServiceDiscoveryBuilder) {

  def get(id: String): Option[ServiceInfo] = {

    val list = builder.buildDiscovery().snapshot()
    val instance = list.find(_.getId == id)
    instance.map(_.getPayload)
  }
}
