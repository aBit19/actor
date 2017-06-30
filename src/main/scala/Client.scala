import akka.actor.{ActorRef, ActorSystem}

import scala.io.StdIn

object Client extends App {
  import example.Printer._
  import example.Greeter._
  val system: ActorSystem = ActorSystem("helloAkka")
  try {
    val printer: ActorRef = system.actorOf(example.Printer.props, "printerActor")

    val hey: ActorRef = system.actorOf(example.Greeter.props("Hey", printer), "HeyGreeter")
    val how: ActorRef = system.actorOf(example.Greeter.props("How you doing", printer), "hydGreeter")

    hey ! WhoToGreet("Akka")
    hey ! WhoToGreet("Antreas")

    how ! WhoToGreet("Jennifer")

    hey ! Greet
    how ! Greet

    println(">>> Enter to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}
