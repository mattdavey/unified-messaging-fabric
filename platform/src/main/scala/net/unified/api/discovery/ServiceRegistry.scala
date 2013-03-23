package net.unified.api.discovery


/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/23/13
 * Time: 9:42 AM
 */

trait ServiceRegistry {

  def registerService(serviceType: String, id: String, info: ServiceInfo)
}
