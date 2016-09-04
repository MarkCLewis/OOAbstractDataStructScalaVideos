package polymorphism

import scalafx.scene.paint.Color

class MutableSquare(_length: Double, c: Color) extends MutableRectangle(_length, _length, c) {
  override def width_=(w: Double) = {
    width = w
    height = w
  }
}