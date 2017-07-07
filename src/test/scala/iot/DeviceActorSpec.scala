package iot

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.{BeforeAndAfterEach, FlatSpec}

class DeviceActorSpec extends FlatSpec with BeforeAndAfterEach {


  "Device actor" should "reply with an None value when there is no temperature" in {
    implicit val system = ActorSystem("Test")
    val device = system.actorOf(Device.props("group", "device"))
    val probe = TestProbe()

    device.tell(Device.ReadTemperature(42), probe.ref)
    val response = probe.expectMsgType[Device.RespondTemperature]

    assert(response.requestId === 42)
    assert(response.temperature === None)
  }
  it should "reply with the last recorded temperature" in {
    implicit val system = ActorSystem("Test")
    val device = system.actorOf(Device.props("group", "device"))
    val probe = TestProbe()

    device.tell(Device.RecordTemperature(1, 23), probe.ref)
    var response = probe.expectMsgType[Device.TemperatureRecorded]
    assert(response.requestId === 1)

    device.tell(Device.RecordTemperature(2, 25), probe.ref)
    response = probe.expectMsgType[Device.TemperatureRecorded]
    assert(response.requestId === 2)

    device.tell(Device.ReadTemperature(1), probe.ref)
    var respondResponse = probe.expectMsgType[Device.RespondTemperature]
    assert(respondResponse.requestId === 1)
    assert(respondResponse.temperature === Some(25))

  }
}
