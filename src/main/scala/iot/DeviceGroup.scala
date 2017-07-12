package iot
import akka.actor.{Actor, ActorLogging, Props}

class DeviceGroup private (val groupId: String) extends Actor with ActorLogging {

}

object DeviceGroup {
  def props(groupId: String): Props = Props(new DeviceGroup(groupId))
}

