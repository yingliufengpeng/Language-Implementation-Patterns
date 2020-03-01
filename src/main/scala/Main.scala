
import parsing.Lexers
import parsing.Lexers.Lexer
import parsing.Parsers.{ListParser, LookAheadParser}
import parsing.Tokens.Token

import scala.collection.mutable.ArrayBuffer

object Main {

  def main(args: Array[String]): Unit = {

    val lexer = new Lexer.ListLexer("[ aa , b , c , d]")
    val parser = new ListParser(lexer)

    parser.list()

    val lexer2 = new Lexers.Lexer.ListLexer("[ aa, bb, c, dd = mm]")
    val parser2 = new LookAheadParser(lexer2)

    parser2.list()

//    val tokens = ArrayBuffer.empty[Token]
//
//    var flag = true
//
//     //这样写代码感觉真的是很low
//    while (flag) {
//      val t = lexer.nextToken()
//      println(s"token is $t")
//      if (t.`type` == Lexer.EOF_TYPE)
//        flag = false
//      else
//        tokens += t
//
//    }

//    @scala.annotation.tailrec
//    def go(): Unit = {
//
//      val t = lexer.nextToken()
//      println(s"token is $t")
//
//      if (t.`type`== Lexer.EOF_TYPE) {}
//      else {
//        tokens += t
//        go()
//      }
//
//    }
//
//    go()


  }
}
