
name := "example dotty project"

version := "0.1-SNAPSHOT"

scalaVersion := "0.24.0-RC1"

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "0.7.7" % Test
)

testFrameworks += new TestFramework("munit.Framework")
