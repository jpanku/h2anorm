ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "h2anorm"
  )

libraryDependencies ++= Seq(
  "org.playframework.anorm" %% "anorm" % "2.6.10",
  "com.h2database" % "h2" % "2.1.212"
)


