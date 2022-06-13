import anorm._
import anorm.SqlParser.str
import java.sql.Connection
import java.sql.DriverManager

object Main extends App {
  println("Main executed")

  implicit val conn: Connection =
    DriverManager.getConnection("jdbc:h2:mem:database;MODE=MYSQL", "sa", "")

  private def createDbStructure(): Unit = {
    val sql = """
      create table if not exists person (
        id int auto_increment primary key,
        name varchar(255) not null,
        last_name varchar(255) not null);"""
    SQL(sql).execute()
  }

  private def insertDbData(): Unit = {
    val sqlIns =
      """insert into person(name, last_name)
                values ('Juan Manuel', 'Jaramillo')"""
    SQL(sqlIns).execute()
  }

  private def selectDbData(): Unit = {
    val sqlSelect =
      """select name, last_name from person"""
    val parser =
      str("name") ~ str("last_name") map { case c1 ~ c2 =>
        (c1, c2)
      }
    val name: (String, String) =
      SQL(sqlSelect) on ("id" -> 0) as parser.single
    println(s"${name._1} ${name._2}")
  }

  createDbStructure()
  println("createDbStructure: done!")
  insertDbData()
  println("insertDbData: done!")
  selectDbData()
  conn.close()
}
