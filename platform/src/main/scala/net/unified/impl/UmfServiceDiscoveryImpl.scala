package net.unified.impl

import net.unified.api.{ServiceInfo, UmfServiceDiscovery}
import com.netflix.curator.x.discovery.details.ServiceDiscoveryImpl

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 5:25 PM
 */

class UmfServiceDiscoveryImpl extends ServiceDiscoveryImpl[ServiceInfo](null, null, null, null) with UmfServiceDiscovery {

}
