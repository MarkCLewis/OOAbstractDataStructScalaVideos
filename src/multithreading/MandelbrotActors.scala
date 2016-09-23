package multithreading

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.BalancingPool
import akka.actor.Actor
import scalafx.application.Platform
import scalafx.scene.image.PixelWriter
import scalafx.scene.paint.Color

object MandelbrotActors extends JFXApp {
  val MaxCount = 10000
  val ImageSize = 600
  val XMin = -1.5
  val XMax = 0.5
  val YMin = -1.0
  val YMax = 1.0

  case class Complex(real: Double, imag: Double) {
    def +(that: Complex) = Complex(real + that.real, imag + that.imag)
    def *(that: Complex) = Complex(real * that.real - imag * that.imag,
      real * that.imag + imag * that.real)
    def mag = math.sqrt(real * real + imag * imag)
  }

  def mandelCount(c: Complex): Int = {
    var cnt = 0
    var z = Complex(0, 0)
    while (cnt < MaxCount && z.mag < 4) {
      z = z * z + c
      cnt += 1
    }
    cnt
  }

  case class Line(row: Int, y: Double)

  class LineActor(pw: PixelWriter) extends Actor {
    def receive = {
      case Line(row, y) =>
        for (j <- 0 until ImageSize) {
          val x = XMin + j * (XMax - XMin) / ImageSize
          val cnt = mandelCount(Complex(x, y))
          Platform.runLater {
            pw.setColor(j, row, if (cnt == MaxCount) Color.Black else {
              val scale = 10 * math.sqrt(cnt.toDouble / MaxCount) min 1.0
              Color(scale, 0, 0, 1)
            })
          }
        }
    }
  }

  val system = ActorSystem("MandelSystem")

  stage = new JFXApp.PrimaryStage {
    title = "Actor Mandelbrot"
    scene = new Scene(ImageSize, ImageSize) {
      val image = new WritableImage(ImageSize, ImageSize)
      content = new ImageView(image)
      val router = system.actorOf(BalancingPool(16).props(Props(new LineActor(image.pixelWriter))), "Pool")
      for (i <- 0 until ImageSize) {
        val y = YMin + i * (YMax - YMin) / ImageSize
        router ! Line(i, y)
      }
    }
  }
}