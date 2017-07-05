package iot

import akka.actor.ActorSystem

import scala.io.StdIn

object IoTApp extends App {
  val system = ActorSystem("IoT")
  try {
    system.actorOf(IoTSupervisor.props())
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}
