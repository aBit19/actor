package iot

import akka.actor.{Actor, ActorLogging, Props}

class DeviceManager extends Actor with ActorLogging {
  override def receive: Receive = Actor.emptyBehavior
}

object DeviceManager {
  def props(): Props = Props[DeviceManager]

  final case class RequestTrackDevice(groupId: String, deviceId: String)
  case object DeviceRegistered
}
