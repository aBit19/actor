package iot

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.{BeforeAndAfterEach, FlatSpec}

class DeviceActorSpec extends FlatSpec with BeforeAndAfterEach {
  implicit val system = ActorSystem("Test")
  "Device actor" should "reply with an None value when there is no temperature" in {
    val probe = TestProbe()
    val device = system.actorOf(Device.props("group", "device"))

    device.tell(Device.ReadTemperature(42), probe.ref)
    val response = probe.expectMsgType[Device.RespondTemperature]

    assert(response.requestId === 42)
    assert(response.temperature === None)
  }
}
