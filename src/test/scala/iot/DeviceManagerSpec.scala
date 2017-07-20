package iot

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.FlatSpec

class DeviceManagerSpec extends FlatSpec {
  import DeviceManager.RequestTrackDevice

  "A Device manager " should " be able to register a new device" in {
    implicit val system = ActorSystem("test")
    val probe = TestProbe()
    val deviceGroup = system.actorOf(DeviceGroup.props("test"))

    deviceGroup.tell(RequestTrackDevice("test", "test"), probe.ref)

    probe.expectMsg(DeviceManager.DeviceRegistered)
    assert(probe.lastSender !== deviceGroup)
    val newActor = probe.lastSender

    deviceGroup.tell(RequestTrackDevice("test", "test"), probe.ref)

    assert(probe.lastSender === newActor)

  }

}
