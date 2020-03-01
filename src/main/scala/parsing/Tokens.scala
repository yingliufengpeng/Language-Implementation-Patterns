package parsing

object Tokens {
  final val INVALID_TOKEN_TYPE = 0 // to be explicit
  final val EOF = -1 // EOF token type

  case class Token(`type`: Int, text: String) {
    override def toString: String = s"<$text >"
  }
}
