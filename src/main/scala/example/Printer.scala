package example

import akka.actor.{Actor, ActorLogging, Props}

class Printer extends Actor with ActorLogging {
  import Printer._

  override def receive: Receive = {
    case Greeting(x) => log.info(x)
  }
}

object Printer {
  def props: Props = Props[Printer]
  final case class Greeting(msg: String)
}
