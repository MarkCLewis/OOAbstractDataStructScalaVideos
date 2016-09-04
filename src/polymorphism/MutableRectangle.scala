package polymorphism

import scalafx.scene.paint.Color

class MutableRectangle(private var _width: Double, private var _height: Double, val color: Color) 
    extends Shape with HasColor {
  def width = _width
  def height = _height
  def width_=(w: Double) = _width = w
  def height_=(h: Double) = _height = h
  override def area: Double = width * height
  override def perimeter: Double = 2 * (width + height)
}