package polymorphism

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Rectangle(val width: Double, val height: Double, val color: Color) extends Shape with HasColor {
  override def area: Double = width * height
  override def perimeter: Double = 2 * (width + height)
  override def draw(gc: GraphicsContext): Unit = {
    super.draw(gc)
    gc.fillRect(0, 0, width, height)
  }
}