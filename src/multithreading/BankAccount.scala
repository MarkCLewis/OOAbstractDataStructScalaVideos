package multithreading

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class BankAccount(private var _balance: Int) {
  def balance = _balance
  
  def deposit(amount: Int): Boolean = synchronized {
    if (amount < 0) false
    else {
      _balance += amount
      true
    }
  }

  def withdraw(amount: Int): Boolean = synchronized {
    if (amount < 0 || amount > _balance) false
    else {
      _balance -= amount
      true
    }
  }
}

object BankAccount extends App {
  val acc = new BankAccount(0)
  for(i <- (1 to 1000000).par) {
    acc.deposit(1)
  }
  println(acc.balance)
  
  var cnt = 0
  Future { for(i <- 1 to 1000000) cnt += 1 }.foreach { _ => println("f1 done") }
  Future { for(i <- 1 to 1000000) cnt += 1 }.foreach { _ => println("f2 done") }
  
  Thread.sleep(100)
  println(cnt)
  
  import collection.mutable
  var b1 = mutable.Buffer[String]()
  var b2 = mutable.Buffer[String]()
  
  def doWork(count: Int): Unit = Thread.sleep(count)
  
  def useBuffers(buf1: mutable.Buffer[String], buf2: mutable.Buffer[String]): Unit = {
    buf1.synchronized {
      doWork(1000)
      buf2.synchronized {
        doWork(1000)
      }
    }
  }
  
  Future { useBuffers(b1, b2) }.foreach( _ => println("Call 1 done."))
  Future { useBuffers(b2, b1) }.foreach( _ => println("Call 2 done."))
  
  Thread.sleep(3000)
  println("Main done")
}