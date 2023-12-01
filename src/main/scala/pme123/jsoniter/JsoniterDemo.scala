package pme123.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import pme123.jsoniter.JsoniterDemo.SimpleEnum.A

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

  case class MyCaseClass(name: String = "Meier", firstName: Option[String] = Some("Peter"), isFunny: Option[Boolean] = None)
  given JsonValueCodec[MyCaseClass] = JsonCodec

  case class SimpleEnumDemo(myEnum: SimpleEnum = SimpleEnum.A)
  given JsonValueCodec[SimpleEnumDemo] = JsonCodec
  enum SimpleEnum:
    case A,B, C
  given JsonValueCodec[SimpleEnum] = JsonEnumCodec

  case class SumEnumDemo(myEnum: SumEnum = SumEnum.A())
  given JsonValueCodec[SumEnumDemo] = JsonCodec
  enum SumEnum:
    case A(name: String = "myName", other: Option[String] = Some("hello"))
    case B(title: String = "TITLE")

  case class RequiredField(name: String)
  given JsonValueCodec[RequiredField] = JsonCodec

