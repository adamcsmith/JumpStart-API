import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "JumpStart"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(

    // Add your project dependencies here,
    // "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
    // "org.mongodb" % "mongo-java-driver" % "2.10.1",
    "mysql" % "mysql-connector-java" % "5.1.18",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )
}