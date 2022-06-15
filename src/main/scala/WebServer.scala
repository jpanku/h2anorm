import Implicits.conn
import Implicits.executionContext
import Implicits.system
import Implicits.userJsonFormat
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn

object WebServer extends App {

  private def response(text: String) = {
    HttpEntity(
      ContentTypes.`text/html(UTF-8)`,
      s"<h1>$text</h1>"
    )
  }

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  val route =
    path("create-table") {
      post {
        complete {
          DBHandler.createDbStructure()
          response("Structure created")
        }
      }
    } ~ path("insert-data") {
      post {
        entity(as[Person]) { person =>
          complete {
            DBHandler.insertDbData(person)
            response("Data inserted")
          }
        }
      }
    } ~ path("user") {
      get {
        parameter("id") { id =>
          complete {
            DBHandler.selectDbData(id)
          }
        }
      }
    }

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
  println(s"Server started...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

  conn.close()
}
