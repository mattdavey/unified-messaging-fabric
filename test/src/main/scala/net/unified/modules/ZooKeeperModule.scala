package net.unified.modules

import com.google.inject.{Scopes, Provider, Inject, AbstractModule}
import com.netflix.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import com.netflix.curator.retry.ExponentialBackoffRetry
import com.netflix.curator.RetryPolicy
import com.google.inject.name.Named


/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 6:05 PM
 */

object ZooKeeperModule extends AbstractModule {

  def configure() {

    this.bind(classOf[RetryPolicy]).toInstance(new ExponentialBackoffRetry(1000, 3))
    this.bind(classOf[CuratorFramework]).toProvider(classOf[CuratorFrameworkProvider]).in(Scopes.SINGLETON)
  }

  private class CuratorFrameworkProvider @Inject()(@Named("zoo-connect") connectionString: String,
                                                   retryPolicy: RetryPolicy) extends Provider[CuratorFramework]() {
    def get(): CuratorFramework = {
      val zooClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy)
      zooClient.start()
      zooClient
    }
  }

}
