package multithreading2

object WaitCounting extends App {
  val numThreads = 3
  val handOff = Array.fill(numThreads)(false)
  val threads = Array.tabulate(numThreads) { i =>
    new Thread {
      override def run: Unit = {
        println("Start " + i)
        for (j <- 1 to 5) {
          WaitCounting.synchronized {
            while (!handOff(i)) {
              WaitCounting.wait()
            }
            handOff(i) = false
            println(i + " : " + j)
            handOff((i+1) % numThreads) = true
            WaitCounting.notifyAll()
          }
        }
      }
    }
  }
  threads.foreach(_.start)
  Thread.sleep(1000)
  println("Notify first")
  handOff(0) = true
  synchronized { notifyAll() }
}