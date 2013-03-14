package net.unified

import org.vertx.java.core.{Handler, Vertx}
import org.vertx.java.core.http.HttpServerRequest

/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/13/13
 * Time: 7:37 PM
 */

object DashboardWebServer {

  def create(vertx: Vertx) = {
    vertx.createHttpServer().requestHandler(new Handler[HttpServerRequest] {
      def handle(request: HttpServerRequest) {
        val file = if (request.uri == "/") "index.html" else request.uri
        request.response.sendFile(s"dashboard/src/main/site/$file")
      }
    })
  }
}
