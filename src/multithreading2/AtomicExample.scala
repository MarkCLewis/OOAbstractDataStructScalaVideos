package multithreading2

import java.util.concurrent.atomic.AtomicInteger

object AtomicExample extends App {
  {
    var cnt = 0
    val start = System.nanoTime()
    for(i <- 1 to 10000000) cnt += 1
    println("Sequential "+cnt+" "+(System.nanoTime()-start)*1e-9)
  }
  {
    var cnt = 0
    val start = System.nanoTime()
    for(i <- (1 to 10000000).par) cnt += 1
    println("Race "+cnt+" "+(System.nanoTime()-start)*1e-9)
  }
  {
    var cnt = 0
    val start = System.nanoTime()
    for(i <- (1 to 10000000).par) AtomicExample.synchronized { cnt += 1 }
    println("Sync "+cnt+" "+(System.nanoTime()-start)*1e-9)
  }
  {
    var cnt = new AtomicInteger(0)
    val start = System.nanoTime()
    for(i <- (1 to 10000000).par) cnt.incrementAndGet()
    println("Race "+cnt+" "+(System.nanoTime()-start)*1e-9)
  }
}