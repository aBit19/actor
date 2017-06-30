package example
import akka.actor.{Actor, ActorRef, Props}

class Greeter private (private val message: String, private val printer: ActorRef) extends Actor {
  import Greeter._
  import Printer._

  private var greeting = ""

  override def receive: Receive = {
    case WhoToGreet(who) => greeting = s"$message, $who"
    case Greet => printer ! Greeting(greeting)
    case _ =>
  }
}

object Greeter {

  def props(msg: String, printer: ActorRef) = Props(new Greeter(msg, printer))
  //Messages
  final case class WhoToGreet(who: String)
  case object Greet
}