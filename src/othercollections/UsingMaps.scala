package othercollections

import io.StdIn._

object UsingMaps extends App {
  case class NameInfo(gender: Char, year: Int, name: String, count: Int)
  val stateInfo = for (stateFile <- new java.io.File("BabyNames").list(); if stateFile.endsWith(".TXT")) yield {
    val source = io.Source.fromFile("BabyNames/" + stateFile)
    val info = source.getLines().filter(_.nonEmpty).map { line => 
      val p = line.split(",")
      NameInfo(p(1)(0), p(2).toInt, p(3), p(4).toInt)
    }.toArray.groupBy(_.name)
    source.close()
    (stateFile.take(2), info)
  }
  var input = ""
  while(input != "quit") {
    println("What name are you interested in?")
    val name = readLine()
    for((state, info) <- stateInfo) {
      println(state+" : "+info(name).maxBy(_.count))
    }
  }
}