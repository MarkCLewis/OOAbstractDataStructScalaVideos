package polymorphism

import scalafx.scene.paint.Color

class BadRectangle(private var _width: Double, private var _height: Double) extends Rectangle(_width, _height, Color.Red) {
  def width_=(w: Double) = _width = w
  def height_=(h: Double) = _height = h
  override def area: Double = _width * _height
  override def perimeter: Double = 2 * (_width + _height)
}