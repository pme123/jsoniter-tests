package pme123.jsoniter

object JsoniterDemo:

  case class MyCaseClass(name: String = "Meier", firstName: Option[String] = Some("Peter"), isFunny: Option[Boolean] = None)
  given InOutCodec[MyCaseClass] = deriveInOutCodec
  given ApiSchema[MyCaseClass] = deriveApiSchema

  case class SimpleEnumDemo(myEnum: SimpleEnum = SimpleEnum.A)
  given InOutCodec[SimpleEnumDemo] = deriveInOutCodec
  given ApiSchema[SimpleEnumDemo] = deriveApiSchema

  enum SimpleEnum:
    case A,B, C
  given InOutCodec[SimpleEnum] = deriveInOutEnumCodec
  given ApiSchema[SimpleEnum] = deriveApiEnumSchema

  case class SumEnumDemo(myEnum: SumEnum = SumEnum.A())
  given InOutCodec[SumEnumDemo] = deriveInOutCodec
  given ApiSchema[SumEnumDemo] = deriveApiSchema

  enum SumEnum:
    case A(name: String = "myName", other: Option[String] = Some("hello"))
    case B(title: String = "TITLE")
  given ApiSchema[SumEnum] = deriveApiSchema

  case class RequiredField(name: String)
  given InOutCodec[RequiredField] = deriveInOutCodec
  given ApiSchema[RequiredField] = deriveApiSchema

