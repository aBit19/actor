package example

import org.scalatest._
import akka.actor.{Actor, Props, ActorSystem}
import akka.testkit._
import scala.concurrent.duration._
import Greeter._
import Printer._