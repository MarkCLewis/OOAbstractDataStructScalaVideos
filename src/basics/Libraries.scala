package basics

import scala.io.StdIn._
import scala.io.Source
import java.io.PrintWriter

object Libraries {
  def main(args:Array[String]):Unit = {
    val source = Source.fromFile("matrix.txt")
    val lines = source.getLines()
    val matrix = lines.map(_.split(" ").map(_.toDouble)).toArray
    source.close()
    
    val pw = new PrintWriter("rowSums.txt")
    matrix.foreach { row => pw.println(row.sum) }
    pw.close()
    
//    println("What is your name?")
//    val name = readLine()
//    println("How old are you?")
//    val age = readInt()
    
    val Array(a,b,c) = "one two three".split(" ")
    
//    val lst = buildList()
//    println(concatStrings(lst))
    
    grade(assignments = List(45, 98),  tests = List(83))
    
    val plus3 = add(3)_
    val eight = plus3(5)
    
    println(threeTuple(math.random))
  }
  
  def add(x: Int)(y: Int):Int = x+y
  
  def threeTuple(a: => Double):(Double, Double, Double) = {
    (a,a,a)
  }
  
  def buildList(): List[String] = {
    val input = readLine()
    if (input == "quit") Nil
    else input :: buildList()
  }
  
  def concatStrings(words: List[String]): String = {
    if (words.isEmpty) ""
    else words.head + concatStrings(words.tail)
  }

  def concatStringsPat(words: List[String]): String = words match {
    case Nil => ""
    case h :: t =>  h + concatStringsPat(t)
  }
  
  def grade(quizzes:List[Int] = Nil, assignments:List[Int] = Nil, tests:List[Int] = Nil):Double = {
    0  
  }
}