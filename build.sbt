
lazy val commonSettings = Seq(
  organization := "org.greenrd",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "Project Seraphim",
    libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "3.0.0" % "test",
        "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
        "ch.qos.logback" % "logback-classic" % "1.1.7",
        "commons-io" % "commons-io" % "2.5" % "test")
  )
