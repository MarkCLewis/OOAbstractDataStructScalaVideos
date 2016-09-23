package multithreading

import io.StdIn._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Await
import scala.concurrent.duration._

object FutureTest extends App {
  println("This if first.")
  val f = Future {
    println("Printing in the future.")
  }
  Thread.sleep(1)
  println("This is last.")

  val f2 = Future {
    for (i <- 1 to 30) yield ParallelCollect.fib(i)
    //throw new RuntimeException("Bad.")
  }
  println(Await.result(f2, 5.seconds))
  //  f2.onComplete {
  //    case Success(n) => println(n)
  //    case Failure(ex) => println("Something went wrong. "+ex)
  //  }

  val page1 = Future {
    "Google "+io.Source.fromURL("http://www.google.com").take(100).mkString
  }
  val page2 = Future {
    "Facebook "+io.Source.fromURL("http://www.facebook.com").take(100).mkString
  }
  val page3 = Future {
    "Twitter "+io.Source.fromURL("http://www.twitter.com").take(100).mkString
  }
  val pages = List(page1, page2, page3)
  
//  val firstPage = Future.firstCompletedOf(pages)
//  firstPage.foreach(println)
  
  val allPages = Future.sequence(pages)
  allPages.foreach(println)

  Thread.sleep(5000)
}