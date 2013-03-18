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
      private var prices = config.symbols.map((_, BigDecimal(rnd.nextDouble() * 100))).toMap

      def run() {

        config.symbols.foreach(symbol => {
          val skip = rnd.nextBoolean()
          if (!skip) {
            val rndWalk = BigDecimal(rnd.nextDouble() * 0.01 * (if (rnd.nextBoolean()) 1.0 else -1.0))
            val prevPrice = prices(symbol)
            val walkedPrice = (prevPrice + rndWalk).setScale(4, BigDecimal.RoundingMode.HALF_UP)
            val price = if (walkedPrice < 0) BigDecimal(0) else walkedPrice
            prices = prices updated(symbol, price)

            val topic = config.id + "." + symbol
            publisher.publish(topic, price)
          }
        })
      }
    }, 3000, 250, TimeUnit.MILLISECONDS)
  }
}
