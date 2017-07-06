package iot

import akka.actor.{Actor, ActorLogging, Props}

class Device private (groupId: String, deviceId: String) extends Actor with ActorLogging {
  import iot.Device._
  private var temperature: Option[Double] = None
  override def preStart(): Unit = log.info("Device actor {}-{}", groupId, deviceId)
  override def postStop(): Unit = log.info("Device actor {}-{}", groupId, deviceId)

  override def receive: Receive = {
    case ReadTemperature(requestId) =>
      sender() ! RespondTemperature(requestId, temperature)
  }

}

object Device {
  def props(groupId: String, deviceId: String): Props =  Props(new Device(groupId, deviceId) )

  // Query protocol
  final case class ReadTemperature(requestId: Long)
  final case class RespondTemperature(requestId: Long, temperature: Option[Double])
}
