package net.unified

import api.discovery.{ServiceTopic, ServiceInfo}
import com.google.inject.Inject
import discovery.UmfServiceDiscoveryBuilder
import net.unified.api.messaging.Publisher
import java.util.concurrent.{TimeUnit, Executors}
import util.Random
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 9:07 AM
 */

class PricingService @Inject()(discoveryBuilder: UmfServiceDiscoveryBuilder,
                               publisher: Publisher) {
  private val logger = LoggerFactory.getLogger(classOf[PricingService])

  def start(config: LineHandlerConfig) {
    logger.info("Starting pricing line handler '{}'", config.id)

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

    logger.info("Registering pricing line handler '{}' with UMF ", config.id)
    val discovery = discoveryBuilder.buildRegistry()
    val info = new ServiceInfo(config.symbols.map(s => ServiceTopic(s, snapshot = false)))
    discovery.registerService("pricing", config.id, info)
  }
}
