package multithreading2

import java.util.concurrent.Executors
import java.util.concurrent.Callable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

object TimeProducts extends App {
  def timeCode(warmUps: Int, timedRuns: Int)(code: => Unit): Double = {
    for (i <- 1 to warmUps) code
    val start = System.nanoTime()
    (1 to timedRuns).foreach { i => code }
    (System.nanoTime() - start) * 1e-9 / timedRuns
  }

  val numThreads = 16
  val es = Executors.newFixedThreadPool(numThreads)

  def parallelProduct(nums: Array[BigInt]): BigInt = {
    val futures = for (i <- 0 until numThreads) yield es.submit(new Callable[BigInt] {
      def call: BigInt = {
        (i * nums.length / numThreads until (i + 1) * nums.length / numThreads).view.map(nums).product
      }
    })
    futures.map(_.get)//.product
    BigInt(1)
  }
  
  def parallelProductSF(nums: Array[BigInt]): BigInt = {
    val futures = for (i <- 0 until numThreads) yield Future {
      (i * nums.length / numThreads until (i + 1) * nums.length / numThreads).view.map(nums).product
    }
    Await.result(Future.sequence(futures), 1.seconds) //.map(_.product), 1.seconds)
    BigInt(1)
  }

  val smallInts = Array.fill(50000)(BigInt(util.Random.nextInt(10) + 10))
  //  println(timeCode(5,10)(smallInts.product))
  println(timeCode(5, 100)(smallInts.par.product))
  println(timeCode(5, 100)(parallelProduct(smallInts)))
  println(timeCode(5, 100)(parallelProductSF(smallInts)))

  es.shutdown()
}