import play.Project._

name := "jumpstart"

version := "1.0"

libraryDependencies ++= Seq(
    javaJdbc,
    javaEbean,
    "org.mindrot" % "jbcrypt" % "0.3m"
    )

playJavaSettings