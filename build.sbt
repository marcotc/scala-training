organization := "com.typesafe.training"

name := "scala-train"

version := "3.0.1"

scalaVersion := Version.scala

// The Typesafe repository
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Dependencies.scalaTrain
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.2.0"

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.6",
  "-encoding", "UTF-8"
)

initialCommands in console := "import com.typesafe.training.scalatrain._"

initialCommands in (Test, console) := (initialCommands in console).value + ",TestData._"
