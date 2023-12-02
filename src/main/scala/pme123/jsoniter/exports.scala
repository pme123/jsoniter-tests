package pme123.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromString, writeToString}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import pme123.jsoniter.JsoniterDemo.SimpleEnumDemo
import sttp.tapir.Schema

import scala.deriving.Mirror

// Jsoniter
type InOutCodec[T] = JsonValueCodec[T]

inline def deriveInOutCodec[A]: InOutCodec[A] = JsonCodecMaker.make {
  CodecMakerConfig
    .withTransientNone(true)
    .withTransientEmpty(true)
    .withTransientDefault(false)
    .withRequireDefaultFields(true)
    .withRequireDiscriminatorFirst(false)
  //.withDiscriminatorFieldName(None)
  //.withCirceLikeObjectEncoding(true)
}

inline def deriveInOutEnumCodec[A]: InOutCodec[A] = JsonCodecMaker.make {
  CodecMakerConfig
    .withTransientDefault(false)
    .withRequireDefaultFields(true)
    .withDiscriminatorFieldName(None)
}

extension[T](obj: T)
  def toJsonStr(using JsonValueCodec[T]): String =
    writeToString(obj)

end extension
extension(jsonStr: String)
  def toObject[T](using JsonValueCodec[T]): T =
    readFromString[T](jsonStr)

end extension

// Tapir
export sttp.tapir.Schema.annotations.description
type ApiSchema[T] = Schema[T]

inline def deriveApiSchema[T](using
                              m: Mirror.Of[T]
                          ): Schema[T] =
  Schema.derived[T]

inline def deriveApiEnumSchema[T](using
                                  m: Mirror.SumOf[T]
                              ): Schema[T] =
  Schema.derivedEnumeration[T].defaultStringBased
