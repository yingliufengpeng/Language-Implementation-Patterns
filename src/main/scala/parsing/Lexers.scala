package parsing

import parsing.Tokens.Token

object Lexers {

  abstract class Lexer(val input: String) {
    import Lexer._
    var p = 0 // index into input of current character
    var c: Char = input(0) // current character, init prime lookahead
    // Move one character; detect "end of file"
    def consume(): Unit = {
      p += 1
      c =  if (p >= input.length) EOF else input(p)
    }

    // Ensure x is next character on the input stream
    def `match`(x: Char): Unit = {
      if (c == x) consume()
      else
        throw new Error(s"expecting $x found $c")
    }

    def isEof: Boolean = c == EOF

    def nextToken(): Token

    def getTokenName(tokenType: Int): String
  }

  object Lexer {

    final val EOF: Char = (-1).toChar //  represent end of file char
    final val EOF_TYPE = 1 // represent EOF token type

    class ListLexer(input: String) extends Lexer(input) {

      override def getTokenName(tokenType: Int): String = ListLexer.tokenNames(tokenType)

      def isLETTER: Boolean = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'

      // NAME : ('a'..'z'| 'A'..'Z') // NAME is sequence of >= 1 letter
      def NAME(): Token = {
        val buf = new StringBuilder
        do {
          buf.append(c)
          consume()
        } while (isLETTER)

        Token(ListLexer.NAME, buf.toString())
      }

      def WS(): Unit = while (ListLexer.WS_LETTER.contains(c)) consume()

      override def nextToken(): Token = {
        // 启动器,引擎启动器,通过这个函数来启动这样引擎的启动!!!
        val t = c match {
              // 首先判断是否已经到达末尾
            case _ if isEof => Token(EOF_TYPE, "<EOF>")
            case ' ' | '\t' | '\n' | '\r' => WS(); nextToken()
            case t@',' => consume(); Token(ListLexer.COMMA, t.toString);
            case t@'[' => consume(); Token(ListLexer.LBRACK, t.toString)
            case t@']' => consume(); Token(ListLexer.RBRACK, t.toString)
            case t@'=' => consume(); Token(ListLexer.EQUALS, t.toString)
              // 条件判断的细节多一些
            case _ if isLETTER => NAME()
            case _ =>
                throw new Error(s"invalid character $c")
          }
        t
      }
    }

    object ListLexer {
      final val NAME = 2
      final val COMMA = 3
      final val LBRACK = 4
      final val RBRACK = 5
      final val EQUALS = 5
      final val tokenNames = List("n/a", "<EOF>", "NAME", "COMMA", "LBBRACK", "RBRACK", "EQUALS")
      final val WS_LETTER = List(' ', '\t', '\r', '\n')
    }
  }

}
