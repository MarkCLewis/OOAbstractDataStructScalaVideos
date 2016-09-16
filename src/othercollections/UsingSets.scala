package othercollections

object UsingSets extends App {
  val year = 2015
  val nationalData = {
    val source = io.Source.fromFile(s"BabyNames/yob$year.txt")
    val names = source.getLines().filter(_.nonEmpty).map(_.split(",")(0)).toSet
    source.close()
    names
  }
  val info = for (stateFile <- new java.io.File("BabyNames").list(); if stateFile.endsWith(".TXT")) yield {
    val source = io.Source.fromFile("BabyNames/" + stateFile)
    val names = source.getLines().filter(_.nonEmpty).map(_.split(",")).
      filter(a => a(2).toInt == year).map(a => a(3)).toArray
    source.close()
    (stateFile.take(2), names)
  }
  val start = System.nanoTime()
  for ((state, snames) <- info) {
    println(state + " " + snames.count(n => nationalData.contains(n)).toDouble / nationalData.size)
  }
  println((System.nanoTime()-start)/1e9)
}