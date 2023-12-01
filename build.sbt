ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "jasoniter-tests"
  )

val jsoniterVersion = "2.25.0"
libraryDependencies ++= Seq(
  // Use the %%% operator instead of %% for Scala.js and Scala Native
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core"   % jsoniterVersion,
  // Use the "provided" scope instead when the "compile-internal" scope is not supported
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided,
  "io.github.iltotore" %% "iron-jsoniter" % "2.3.0",
  "org.scalameta" %% "munit" % "0.7.29" % Test
)

testFrameworks += new TestFramework("munit.Framework")