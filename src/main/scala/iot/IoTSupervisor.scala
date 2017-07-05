package iot

import akka.actor.{Actor, ActorLogging, Props}

class IoTSupervisor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("IoT started")
  override def postStop(): Unit = log.info("IoT stopped")
  override def receive: Receive = Actor.emptyBehavior
}


object IoTSupervisor {
  def props() :Props = Props(new IoTSupervisor)
}