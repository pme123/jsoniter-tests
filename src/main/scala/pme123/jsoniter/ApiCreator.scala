package pme123.jsoniter

import pme123.jsoniter.JsoniterDemo.{MyCaseClass, SimpleEnumDemo}
import sttp.apispec.openapi.OpenAPI
import sttp.apispec.openapi.circe.yaml.*
import sttp.tapir.*
import sttp.tapir.EndpointIO.Example
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.json.jsoniter.*

object ApiCreator extends App:

  val openApiPath: os.Path = os.pwd / "openApi.yml"
  val openApiDocuPath: os.Path = os.pwd / "OpenApi.html"
  val bookInput: EndpointIO[MyCaseClass] = jsonBody[MyCaseClass]
  val myTests = // endpoint.in(path[String]("bookId"))
    endpoint
      .name("My Test Endpoint")
      .in("demo" / "tests")
      .tag("TRARAH")
      .in(bookInput)
      .out(jsonBody[SimpleEnumDemo])

  val codes = // endpoint.in(path[String]("bookId"))
    endpoint
      .name("Enum Sum Types Codes")
      .in("demo" / "getCodes")
      .tag("TRARAH")
      .in(jsonBody[GetCodes.In]
        .examples(
          List(
            Example(
              GetCodes.In.WithLanguage(),
              Some("WithLanguage"),
              None
            ),
            Example(
              GetCodes.In.WithResultKey(),
              Some("WithResultKey"),
              None
            )
          )
        ))
      .out(jsonBody[GetCodes.Out])

  val allEndpoints = Seq(myTests, codes)

  val docs: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(allEndpoints, "My Tests", "1.0")

  writeOpenApi(openApiPath, docs, openApiDocuPath)
  private def writeOpenApi(
      path: os.Path,
      api: OpenAPI,
      docPath: os.Path
  ): Unit =
    if os.exists(path) then
      os.remove(path)
    val yaml = api.toYaml
    os.write(path, yaml)
    println(s"Created Open API $path")
    println(s"See Open API Html $docPath")
  end writeOpenApi
end ApiCreator
