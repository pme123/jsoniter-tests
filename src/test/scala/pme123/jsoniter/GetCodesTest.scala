package pme123.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import pme123.jsoniter.GetCodes.*

class GetCodesTest extends munit.FunSuite {

  test("get Codes") {
    val objExpected: In = In.WithResultKey()
    val json = objExpected.toJsonStr
    val jsonStr = """{"type":"WithResultKey","codeTable":"accountType","resultKey":"2010"}"""
    println(json)
    val obj = readFromString[In](jsonStr)
    assertEquals(json, jsonStr)
    assertEquals(obj, objExpected)
  }

  }
