// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf"))

name := "Iso"

scalaVersion := "2.11.8"

persistLauncher in Compile := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1"
)
