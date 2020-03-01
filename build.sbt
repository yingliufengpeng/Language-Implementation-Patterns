
name := "Language-Implementation-Patterns"

version := "0.1"

scalaVersion := "2.13.1"

//// sbt 0.13.x
antlr4Settings

libraryDependencies += "org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.1"

antlr4Version in Antlr4 := "4.8" // default: 4.7.2

antlr4GenListener in Antlr4 := false // default: true

antlr4GenVisitor in Antlr4 := true // default: false
