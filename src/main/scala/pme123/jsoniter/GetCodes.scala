package pme123.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.writeToString

object GetCodes:

  enum In:
    def codeTable: String
    case WithResultKey(
        codeTable: String = "accountType",
        resultKey: String = "2010",
        customMock: Option[Out.WithResultKey] = None
    )
    case WithLanguage(
        codeTable: String = "accountType",
        language: String = "DE",
        customMock: Option[Out.WithLanguage] = None
    )
    case WithLanguageAndResultKey(
        codeTable: String = "investmentObjectives",
        language: String = "DE",
        resultKey: String = "0",
        customMock: Option[Out.WithLanguageAndResultKey] = None
    )
    case All(
        codeTable: String = "accountType",
        customMock: Option[Out.All] = None
    )
  object In:
    given ApiSchema[In] = deriveApiSchema
    given InOutCodec[In] = deriveInOutCodec

    // needed for asJsoon
  end In

  type CodeTableResult = Map[String, String]

  enum Out:
    case WithResultKey(
        @description(
          """A value for each language, if **NO** language is set and a resultKey is set.
            |- `codeForKeyResult_$LANGUAGE` example: `codeForKeyResult_FR`
            |"""
        )
        codeForKeyResult_DE: String = "Privatkonto",
        codeForKeyResult_FR: String = "Compte privé"
    )
    case WithLanguage(
        codeTableResult: CodeTableResult = tableOutResultExampleDe
    )
    case WithLanguageAndResultKey(
        codeForKeyResult: String = "Keine  Investment-Strategie"
    )
    case All(
        @description(
          "A Map with Language-CodeTableResult of the Requested Code Table, if **NO** language is set."
        )
        codeTableResults: Map[String, CodeTableResult] = tableOutResultsExample
    )
  end Out
  object Out:
    given ApiSchema[Out] = deriveApiSchema
    given InOutCodec[Out] = deriveInOutCodec
    // needed for mocking
    given ApiSchema[Out.WithResultKey] = deriveApiSchema
    given ApiSchema[Out.WithLanguage] = deriveApiSchema
    given ApiSchema[Out.WithLanguageAndResultKey] = deriveApiSchema
    given ApiSchema[Out.All] = deriveApiSchema


  end Out

  lazy val tableOutResultExampleDe =
    Map("1" -> "Kontokorrent CHF", "2010" -> "Privatkonto")
  lazy val tableOutResultExampleFr =
    Map("1" -> "Compte courant CHF", "2010" -> "Compte privé")
  lazy val tableOutResultsExample =
    Map("DE" -> tableOutResultExampleDe, "FR" -> tableOutResultExampleFr)
  
end GetCodes
