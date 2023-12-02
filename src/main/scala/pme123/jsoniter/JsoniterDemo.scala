package pme123.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import pme123.jsoniter.JsoniterDemo.SimpleEnum.A
import sttp.tapir.Schema
import sttp.tapir.json.jsoniter.*

import scala.deriving.Mirror

object JsoniterDemo:

  inline def JsonCodec[A]: JsonValueCodec[A] = JsonCodecMaker.make {
    CodecMakerConfig
      .withTransientNone(true)
      .withTransientEmpty(true)
      .withTransientDefault(false)
      .withRequireDefaultFields(true)
      .withRequireDiscriminatorFirst(false)
      //.withDiscriminatorFieldName(None)
      //.withCirceLikeObjectEncoding(true)
  }

  inline def JsonEnumCodec[A]: JsonValueCodec[A] = JsonCodecMaker.make {
    CodecMakerConfig
      .withTransientDefault(false)
      .withDiscriminatorFieldName(None)
    //.withCirceLikeObjectEncoding(true)
  }

  type ApiSchema[T] = Schema[T]

  inline def deriveSchema[T](using
                             m: Mirror.Of[T]
                            ): Schema[T] =
    Schema.derived[T]

  inline def deriveEnumSchema[T](using
                                 m: Mirror.Of[T]
                                ): Schema[T] =
    Schema.derivedEnumeration[T].defaultStringBased

  case class MyCaseClass(name: String = "Meier", firstName: Option[String] = Some("Peter"), isFunny: Option[Boolean] = None)
  given JsonValueCodec[MyCaseClass] = JsonCodec
  given Schema[MyCaseClass] = deriveSchema

  case class SimpleEnumDemo(myEnum: SimpleEnum = SimpleEnum.A)
  given JsonValueCodec[SimpleEnumDemo] = JsonCodec
  given Schema[SimpleEnumDemo] = deriveSchema

  enum SimpleEnum:
    case A,B, C
  given JsonValueCodec[SimpleEnum] = JsonEnumCodec
  given Schema[SimpleEnum] =deriveEnumSchema

  case class SumEnumDemo(myEnum: SumEnum = SumEnum.A())
  given JsonValueCodec[SumEnumDemo] = JsonCodec
  given Schema[SumEnumDemo] = deriveSchema

  enum SumEnum:
    case A(name: String = "myName", other: Option[String] = Some("hello"))
    case B(title: String = "TITLE")
  given Schema[SumEnum] = deriveSchema

  case class RequiredField(name: String)
  given JsonValueCodec[RequiredField] = JsonCodec
  given Schema[RequiredField] = Schema.derived

