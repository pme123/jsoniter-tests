package pme123.jsoniter
import JsoniterDemo.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import com.github.plokhotnyuk.jsoniter_scala.core.*
import pme123.jsoniter.JsoniterDemo.SumEnum.A

class JsoniterDemoTest extends munit.FunSuite {

  test("case class") {
    val json = writeToString(MyCaseClass())
    val jsonStr = """{"name":"Meier","firstName":"Peter"}"""
    println(json)
    val obj = readFromString[MyCaseClass](jsonStr)
    assertEquals(json, jsonStr)
    assertEquals(obj, MyCaseClass())
  }

  test("case class extra input") {
    val jsonStr = """{"name":"Meier","firstName":"Peter", "hobby": "surfing"}"""
    val obj = readFromString[MyCaseClass](jsonStr)
    assertEquals(obj, MyCaseClass())
  }

  test("case class required field") {
    val jsonStr = """{}"""
    intercept[JsonReaderException](
      readFromString[RequiredField](jsonStr)
    )
  }
  test("case class missing required input") {
    val jsonStr = """{}"""
    intercept[JsonReaderException](
      readFromString[MyCaseClass](jsonStr)
    )
  }
  test("case class no option") {
    val json = writeToString(MyCaseClass(firstName = None))
    val jsonStr = """{"name":"Meier"}"""
    val obj = readFromString[MyCaseClass](jsonStr)
    assertEquals(json, jsonStr)
    assertEquals(obj, MyCaseClass(firstName = None))
  }

  test("simple enum"){
    val json = writeToString(SimpleEnumDemo())
    val jsonStr = """{"myEnum":"A"}"""
    val obj = readFromString[SimpleEnumDemo](jsonStr)
    assertEquals(json, jsonStr)
    assertEquals(obj, SimpleEnumDemo())
  }
  test("sum enum"){
    val json = writeToString(SumEnumDemo())
    println(s"JSON: $json")
    val jsonStr = """{"myEnum":{"type":"A","name":"myName","other":"hello"}}"""
    val obj = readFromString[SumEnumDemo](jsonStr)
    assertEquals(json, jsonStr)
    assertEquals(obj, SumEnumDemo())
  }
  test("sum enum with option"){
    val json = writeToString(SumEnumDemo(A(other = None)))
    println(s"JSON: $json")
    val jsonStr = """{"myEnum":{"type":"A","name":"myName"}}"""
    val obj = readFromString[SumEnumDemo](jsonStr)
    assertEquals(json, jsonStr)
    assertEquals(obj, SumEnumDemo(A(other = None)))
  }
}
