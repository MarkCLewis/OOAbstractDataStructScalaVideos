package multithreading

import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import akka.actor.TypedActor.PreRestart

object SupervisorExample extends App {
  case object CreateChild
  case class SignalChildren(order:Int)
  case class PrintSignal(order:Int)
  case class DivideNumbers(n:Int,d:Int)
  case object BadStuff
  
  class ParentActor extends Actor {
    private var number = 0
    def receive = {
      case CreateChild =>
        context.actorOf(Props[ChildActor],"child"+number)
        number += 1
      case SignalChildren(n) =>
        context.children.foreach(_ ! PrintSignal(n))
    }
    
    override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
      case ae:ArithmeticException => Resume
      case _:Exception => Restart
    }
  }
  
  class ChildActor extends Actor {
    println("Child created.")
    def receive = {
      case PrintSignal(n) => println(n+" "+self)
      case DivideNumbers(n,d) => println(n/d)
      case BadStuff => throw new RuntimeException("Stuff happened")
    }
    override def preStart() = {
      super.preStart()
      println("preStart")
    }
    override def postStop() = {
      super.postStop()
      println("postStop")
    }
    override def preRestart(reason:Throwable, message:Option[Any]) = {
      super.preRestart(reason, message)
      println("Prerestart")
    }
    override def postRestart(reason:Throwable) = {
      super.postRestart(reason)
      println("Postrestart")
    }
  }
  
  val system = ActorSystem("HierarchySystem")
  val actor = system.actorOf(Props[ParentActor],"Parent1")
  val actor2 = system.actorOf(Props[ParentActor],"Parent2")
  
  actor ! CreateChild
//  actor ! CreateChild
  val child0 = system.actorSelection("/user/Parent1/child0")
  child0 ! DivideNumbers(4,0)
  child0 ! DivideNumbers(4,2)
  child0 ! BadStuff
  
  
  Thread.sleep(1000)
  system.terminate()

}