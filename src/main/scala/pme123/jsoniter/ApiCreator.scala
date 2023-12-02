package pme123.jsoniter

import pme123.jsoniter.JsoniterDemo.{MyCaseClass, SimpleEnumDemo}
import sttp.apispec.openapi.OpenAPI
import sttp.tapir.*
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.apispec.openapi.circe.yaml.*
import sttp.tapir.json.jsoniter.*

object ApiCreator extends App:


  val openApiPath: os.Path = os.pwd / "openApi.yml"
  val openApiDocuPath: os.Path = os.pwd / "OpenApi.html"
  val bookInput: EndpointIO[MyCaseClass] = jsonBody[MyCaseClass]
  val booksListing = //endpoint.in(path[String]("bookId"))
    endpoint
      .name("My Test Endpoint")
      .tag("TRARAH")
      .in(bookInput)
      .out(jsonBody[SimpleEnumDemo])


  val docs: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(booksListing, "My Bookshop", "1.0")

  writeOpenApi(openApiPath, docs, openApiDocuPath)
  private def writeOpenApi(
                            path: os.Path,
                            api: OpenAPI,
                            docPath: os.Path
                          ): Unit =
    if (os.exists(path))
      os.remove(path)
    val yaml = api.toYaml
    os.write(path, yaml)
    println(s"Created Open API $path")
    println(s"See Open API Html $docPath")
end ApiCreator

