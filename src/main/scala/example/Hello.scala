package example

import javax.usb.UsbHostManager
import javax.usb.UsbDevice
import javax.usb.UsbHub

import scala.jdk.CollectionConverters._

object Hello extends Greeting with App {
  println(greeting)

  val services = UsbHostManager.getUsbServices
  dump(services.getRootUsbHub, 0)
}

trait Greeting {
  lazy val greeting: String = "hello"


  def dump(device: UsbDevice, level: Int): Unit = {
    var i = 0
    while (i < level) {
      System.out.print("  ")
      i += 1
    }
    System.out.println(device)
    if (device.isUsbHub) {
      val hub = device.asInstanceOf[UsbHub]
      for (child <- hub.getAttachedUsbDevices.asInstanceOf[java.util.List[UsbDevice]].asScala) {
        dump(child, level + 1)
      }
    }
  }
}
