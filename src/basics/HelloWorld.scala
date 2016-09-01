/*
 * This file created for Object-Orientation, Abstraction, 
 * and Data Structures Using Scala
 */

package basics

/**
 * This is the main object for our first application.
 */
object HelloWorld {
  val name = "Pat Cook"
  val value = 42
  var age = 22
  age += 1
  // String, Int, Double, Char, Boolean, Unit
  val t = (1, 2.7, "hi there")
  val (a, b, c) = t
  println(t._1)
  
  val message = name + " is " + (age+1) + " years old."
  val messages2 = s"$name is ${age+1} years old."
  val str = s"The second element of t is ${t._2}."
  value + age
  name == "Pat Cook" * 5
  
  val square: Double => Double = x => x*x
  println(square(3))
  val twice: Double => Double = _ * 2
  val lt: (Double, Double) => Boolean = _ < _
//  def square(x:Double):Double = x*x
  
  
  /**
   * This is the main method for the application.
   * @param args the arguments to the application
   */
  def main(args: Array[String]): Unit = {
    println("Hello World") // This prints something.
    
    var i = 0
    while (i < 10) {
      println(i)
      i += 1  // i = i+1
    }
    
    if(age < 18) {
      println("No admittance.")
    } else {
      println("Come on in.")
    }
    
    val thing = {
      val things2 = 42
      println("In the block.")
      things2
    }
    
    val response = if(age < 18) {
      "No admittance."
    } else {
      "Come on in."
    }
    println(response)
    
    println(if(age < 18) "No admittance." else "Come on in.")

    val stuff = for { 
      i <- 0 until 10
      if i%3==0 || i%5==0
      sqr = i*i
      j <- 'a' to 'c' 
    } yield {
      i -> j
    }
    println(stuff)
    
    val fizzbuzz = for(i <- 1 to 20) yield {
      (i%3, i%5) match {
        case (0, 0) => "fizzbuzz"
        case (0, _) => "fizz"
        case (_, 0) => "buzz"
        case _ => i.toString 
      }
    }
    
    val str = "123"
    val num = try {
      str.toInt
    } catch {
      case ex: NumberFormatException => 0
    }
    println(num)
  }
  
}