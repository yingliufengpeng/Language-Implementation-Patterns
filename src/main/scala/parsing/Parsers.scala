package parsing

import parsing.Lexers.Lexer.ListLexer

object Parsers {

  /**
   *
   * @param input from where do we get tokens
   * @param k how many lookahead symbols
   */
    abstract class Parser(input: Lexers.Lexer, k: Int) {
      var p = 0 // circule index of next token position to fill

      // make lookahead buffer
      val lookaheads: Array[Tokens.Token] = Array.ofDim[Tokens.Token](k)

      // prime buffer with k lookahead
      lookaheads.foreach(_ => consume())

      def consume(): Unit = {
        lookaheads(p) = input.nextToken()
        p = (p + 1) % k
      }

      def LT(i: Int): Tokens.Token = lookaheads((p + i - 1 ) % k) // circular fetch

      def LA(i: Int): Int = LT(i).`type`

      def `match`(x: Int): Unit = {
        if (LA(1) == x) consume()
        else
          throw new Error(s"expecting ${input.getTokenName(x)} : found ${LT(1)}")
      }
    }

    // 该模式为LL(1)模式细节
    class ListParser(lexer: Lexers.Lexer) extends Parser(lexer, 1) {
      /* list : '[' elements ']' // match bracketed list */
      def list(): Unit = {
        `match`(ListLexer.LBRACK)
        elements()
        `match`(ListLexer.RBRACK)
      }

      /* elements : element (',' element)* ; */
      def elements(): Unit = {
        element()
        while (LA(1) == ListLexer.COMMA) {
          `match`(ListLexer.COMMA)
          element()
        }
      }

      /* element: name | list ; element is name or nested list */
      def element(): Unit = {
          LA(1) match {
            case n @ListLexer.NAME => `match`(n)
            case ListLexer.LBRACK => list()
            case _  =>
              throw new Error(s"expecting name or list found ${LT(1)}")
          }

      }
    }

    class LookAheadParser(lexer: Lexers.Lexer) extends Parser(lexer, 2) {
      /* list : '[' elements ']' // match bracketed list */
      def list(): Unit = {
        `match`(ListLexer.LBRACK)
        elements()
        `match`(ListLexer.RBRACK)
      }

      /* elements : element (',' element)* ; */
      def elements(): Unit = {
        element()
        while (LA(1) == ListLexer.COMMA) {
          `match`(ListLexer.COMMA)
          element()
        }
      }

      /* element: NAME '=' NAME | NAME | list  */
      def element(): Unit = {
        LA(1) match {
          case n1 @ListLexer.NAME if LA(2) == ListLexer.EQUALS =>
            `match`(n1) ; `match`(ListLexer.EQUALS) ; `match`(n1)
          case n @ListLexer.NAME => `match`(n)
          case ListLexer.LBRACK => list()
          case _  => throw new Error(s"expecting name or list found ${LT(1)}")

        }

      }
    }
}


