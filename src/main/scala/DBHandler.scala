import Implicits._
import anorm._
import anorm.SqlParser.str
import scala.concurrent.Future

object DBHandler extends App {
  println("Main executed")

  def createDbStructure(): Unit = {
    val sql = """
      create table if not exists person (
        id int auto_increment primary key,
        name varchar(255) not null,
        last_name varchar(255) not null);"""
    SQL(sql).execute()
  }

  def insertDbData(person: Person): Unit = {
    val sqlIns =
      s"""insert into person(name, last_name)
         values ('${person.name}', '${person.lastName}')"""
    SQL(sqlIns).execute()
  }

  def selectDbData(id: String): Future[Person] = {
    val sqlSelect =
      """select name, last_name from person"""
    val parser =
      str("name") ~ str("last_name") map { case c1 ~ c2 =>
        (c1, c2)
      }
    val name: (String, String) =
      SQL(sqlSelect) on ("id" -> id) as parser.single

    Future(Person(name = name._1, lastName = name._2))
  }


}
