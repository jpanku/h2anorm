import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import java.sql.Connection
import java.sql.DriverManager
import scala.concurrent.ExecutionContextExecutor
import spray.json.DefaultJsonProtocol
import spray.json.RootJsonFormat

object Implicits {
  implicit val conn: Connection =
    DriverManager.getConnection("jdbc:h2:mem:database;MODE=MYSQL", "sa", "")

  import DefaultJsonProtocol._
  implicit val userJsonFormat: RootJsonFormat[Person] = jsonFormat2(Person)

  implicit val system: ActorSystem[Nothing] =
    ActorSystem(Behaviors.empty, "my-system")

  implicit val executionContext: ExecutionContextExecutor =
    system.executionContext

}
