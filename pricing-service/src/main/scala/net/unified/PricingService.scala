package net.unified

import api.{ServiceTopic, ServiceInfo}
import com.google.inject.Inject
import discovery.UmfServiceDiscoveryBuilder
import com.netflix.curator.x.discovery.ServiceInstance
import net.unified.messaging.Publisher
import java.util.concurrent.{TimeUnit, Executors}
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 9:07 AM
 */

class PricingService @Inject()(discoveryBuilder: UmfServiceDiscoveryBuilder,
                               publisher: Publisher) {

  def start(config: LineHandlerConfig) {
    val discovery = discoveryBuilder.build()
    val instance = ServiceInstance.builder[ServiceInfo]()
      .name("umf-service").id(config.id)
      .payload(new ServiceInfo(config.symbols.map(s => ServiceTopic(s, snapshot = false))))
      .build()
    discovery.registerService(instance)

    Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable {
      private val rnd = new Random()

      def run() {
        config.symbols.foreach(s => publisher.publish(s, rnd.nextDouble() * 100))
      }
    }, 3000, 500, TimeUnit.MILLISECONDS)
  }
}
