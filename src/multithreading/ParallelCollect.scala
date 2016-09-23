package multithreading

object ParallelCollect extends App {
  def fib(n:Int):Int = if (n < 2) 1 else fib(n-1)+fib(n-2)
  
  for(i <- (30 to 15 by -1).par) {
    println(fib(i))
  }
  
  var i = 0
  for(j <- (1 to 1000000).par) {
    // load i from memory
    // add 1 to register
    // store i to memory
    i += 1
  }
  println(i)
  
}