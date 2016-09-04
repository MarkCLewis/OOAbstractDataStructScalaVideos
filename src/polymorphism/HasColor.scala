package polymorphism

import scalafx.scene.paint.Color
import scalafx.scene.canvas.GraphicsContext

trait HasColor {
  val color: Color
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
  }
}