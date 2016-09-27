package multithreading2

object ThreadSum extends App {
  val nums = Array.fill(1000000)(math.random)
  val numThreads = 10
  val sums = Array.fill(numThreads)(0.0)
  val threads = for (i <- 0 until numThreads) yield new Thread {
    override def run: Unit = {
      for (j <- i * nums.length / numThreads until (i + 1) * nums.length / numThreads) {
        sums(i) += nums(j)
      }
    }
  }
  threads.foreach(_.start)
  threads.foreach(_.join)
  println(sums.sum + " = " + nums.sum)
}