package multithreading2

import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Exchanger
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.Semaphore
import java.util.concurrent.ConcurrentHashMap
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.ArrayBlockingQueue

object ConcurrentDataStructures extends App {
  def doRandomWork(): Int = {
    Thread.sleep(util.Random.nextInt(1000) + 1)
    util.Random.nextInt(100)
  }

  // CyclicBarrier and CountDownLatch
  val numThreads = 5
  val cb = new CyclicBarrier(numThreads)
  val cdl = new CountDownLatch(numThreads)
  val cbFutures = for (i <- 1 to numThreads) yield Future {
    doRandomWork()
    println(s"CB Thread $i awaiting")
    cb.await()
    cdl.countDown()
  }
  println("Main Thread await CDL")
  cdl.await()
  println("CyclicBarrier all done")

  // Exchanger
  val ex = new Exchanger[Int]
  Future {
    val v1 = doRandomWork()
    println(s"Exchange 1 calculated $v1")
    val v2 = ex.exchange(v1)
    println(s"Exchange 1 received $v2")
  }
  Future {
    val v2 = doRandomWork()
    println(s"Exchange 2 calculated $v2")
    val v1 = ex.exchange(v2)
    println(s"Exchange 2 received $v1")
  }

  // BlockingQueue / BlockingDeque
  val bq = new ArrayBlockingQueue[Int](3)
  val numProducts = 7
  val cdl2 = new CountDownLatch(numProducts)
  Future {
    for (i <- 1 to numProducts) {
      val prod = doRandomWork()
      println(s"Produced $prod")
      bq.put(prod)
    }
  }
  Thread.sleep(3000)
  Future {
    for (i <- 1 to numProducts) {
      val cons = bq.take()
      println(s"Consumed $cons")
      cdl2.countDown()
    }
  }
  cdl2.await()

  // ConcurrentHashMap
  val chm = new ConcurrentHashMap[String, String]

  // Semaphore
  val sem = new Semaphore(3)
  for (i <- 1 to numThreads*2) Future {
    sem.acquire()
    println(s"Running thread $i")
    Thread.sleep(2000)
    chm.put(s"t$i", "Running")
    sem.release()
  }

  while (chm.size() < numThreads*2) {
    Thread.sleep(10)
  }
  println(chm)
}