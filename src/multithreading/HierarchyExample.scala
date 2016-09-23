package multithreading

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

object HierarchyExample extends App {
  case object CreateChild
  case class SignalChildren(order:Int)
  case class PrintSignal(order:Int)
  
  class ParentActor extends Actor {
    private var number = 0
    def receive = {
      case CreateChild =>
        context.actorOf(Props[ChildActor],"child"+number)
        number += 1
      case SignalChildren(n) =>
        context.children.foreach(_ ! PrintSignal(n))
    }
  }
  
  class ChildActor extends Actor {
    def receive = {
      case PrintSignal(n) => println(n+" "+self)
    }
  }
  
  val system = ActorSystem("HierarchySystem")
  val actor = system.actorOf(Props[ParentActor],"Parent1")
  val actor2 = system.actorOf(Props[ParentActor],"Parent2")
  
  actor ! CreateChild
  actor ! SignalChildren(1)
  actor ! CreateChild
  actor ! CreateChild
  actor ! SignalChildren(2)
  
  actor2 ! CreateChild
  val child0 = system.actorSelection("/user/Parent2/child0")
  child0 ! PrintSignal(3)
  
  Thread.sleep(1000)
  system.terminate()
}