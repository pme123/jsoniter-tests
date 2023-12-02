ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "jasoniter-tests"
  )

val osLibVersion = "0.9.1"
val jsoniterVersion = "2.25.0"
val tapirVersion = "1.9.3"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "os-lib" % osLibVersion,
  // Jsoniter
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core"   % jsoniterVersion,
  // Use the "provided" scope instead when the "compile-internal" scope is not supported
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided,
  "io.github.iltotore" %% "iron-jsoniter" % "2.3.0",
  // Tapir
  "com.softwaremill.sttp.tapir" %% "tapir-redoc-bundle" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-jsoniter-scala" % tapirVersion,
  "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % "0.7.1", // see https://github.com/softwaremill/sttp-apispec


// Testing
  "org.scalameta" %% "munit" % "0.7.29" % Test
)

testFrameworks += new TestFramework("munit.Framework")