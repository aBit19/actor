package iot

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}

class DeviceGroup private (val groupId: String) extends Actor with ActorLogging {
  import DeviceManager._

  private var deviceIdToActor = Map.empty[String, ActorRef]
  private var actorRefToDeviceId = Map.empty[ActorRef, String]

  override def preStart(): Unit = log.info(s"Device ${groupId} has been created")

  override def receive: Receive = {
    case trackMsg @ RequestTrackDevice(grId, _) if grId == groupId =>
      deviceIdToActor.get(trackMsg.deviceId) match {
        case Some(actor) => actor forward trackMsg
        case None => {
          val deviceActor: ActorRef = context.actorOf(Device.props(trackMsg.groupId, trackMsg.deviceId), s"device-${trackMsg.deviceId}")
          deviceIdToActor += trackMsg.deviceId -> deviceActor
          actorRefToDeviceId += deviceActor -> trackMsg.deviceId
          deviceActor forward trackMsg
          context.watch(deviceActor)
        }
      }
    case msg @ RequestTrackDevice(_, _) => log.warning(s"Ignoring ${msg}")

    case Terminated(actor) => {
      log.info(s"$actor has been terminated\nRemoving it from the system")
      deviceIdToActor - actorRefToDeviceId(actor)
      actorRefToDeviceId - actor
    }
  }

}

object DeviceGroup {
  def props(groupId: String): Props = Props(new DeviceGroup(groupId))
}

