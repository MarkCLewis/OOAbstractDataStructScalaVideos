package multithreading

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import akka.util.Timeout

object ActorCountDown extends App {
  case class StartCounting(n:Int, other:ActorRef)
  case class CountDown(n:Int)
  
  class CountDownActor extends Actor {
    def receive = {
      case StartCounting(n, other) =>
        println(n)
        other ! CountDown(n-1)
      case CountDown(n) =>
        println(self)
        if(n>0) {
          println(n)
          sender ! CountDown(n-1)
        } else {
          context.system.terminate()
        }
    }
  }
  
  val system = ActorSystem("SimpleSystem")
  val actor1 = system.actorOf(Props[CountDownActor],"CountDown1")
  val actor2 = system.actorOf(Props[CountDownActor],"CountDown2")
  
  actor1 ! StartCounting(10, actor2)
}