package net.unified

import org.vertx.java.core.{Handler, Vertx}
import org.vertx.java.core.http.{RouteMatcher, HttpServerRequest}
import com.google.inject.Inject
import rest.ServiceDetailsHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.vertx.java.core.buffer.Buffer
import org.jboss.netty.handler.codec.http.HttpResponseStatus

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 7:37 PM
 */

class DashboardWebServer @Inject()(serviceDetails: ServiceDetailsHandler) {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def create(vertx: Vertx) = {

    val routes = new RouteMatcher()

    routes.get("/services/:id", new Handler[HttpServerRequest] {
      def handle(request: HttpServerRequest) {
        val response = serviceDetails.apply(request.params().get("id"))
        response match {
          case Some(details) => request.response.end(new Buffer(mapper.writeValueAsBytes(details)))
          case None => {
            request.response.statusCode = HttpResponseStatus.NOT_FOUND.getCode
            request.response.statusMessage = HttpResponseStatus.NOT_FOUND.getReasonPhrase
            request.response.end()
          }
        }

      }
    })

    routes.getWithRegEx(".*", new Handler[HttpServerRequest] {
      def handle(request: HttpServerRequest) {
        val file = if (request.uri == "/") "index.html" else request.uri
        request.response.sendFile(s"dashboard/src/main/site/$file")
      }
    })

    vertx.createHttpServer().requestHandler(routes)
  }
}
