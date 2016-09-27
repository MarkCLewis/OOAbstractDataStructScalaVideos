package multithreading2

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureSum extends App {
  val nums = Array.fill(1000000)(math.random)
  val numThreads = 10
  val sums = for (i <- 0 until numThreads) yield Future {
    var sum = 0.0
    for (j <- i * nums.length / numThreads until (i + 1) * nums.length / numThreads) {
      sum += nums(j)
    }
    sum
  }
  Future.sequence(sums).foreach(s => println(s.sum + " = " + nums.sum))
  Thread.sleep(1000)
}