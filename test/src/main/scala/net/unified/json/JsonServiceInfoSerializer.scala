package net.unified.json

import com.netflix.curator.x.discovery.details.InstanceSerializer
import com.netflix.curator.x.discovery.ServiceInstance

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.`type`.TypeReference
import net.unified.api.discovery.ServiceInfo


/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 10:21 AM
 */

class JsonServiceInfoSerializer extends InstanceSerializer[ServiceInfo] {
  private val mapper: ObjectMapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def serialize(instance: ServiceInstance[ServiceInfo]): Array[Byte] = {
    mapper.writeValueAsBytes(instance)
  }

  def deserialize(bytes: Array[Byte]): ServiceInstance[ServiceInfo] = {
    mapper.readValue(bytes, new TypeReference[ServiceInstance[ServiceInfo]] {})
  }
}
