package guisgraphics.drawing

import scalafx.scene.canvas.GraphicsContext

trait Drawable {
  val drawing: Drawing
  def draw(gc: GraphicsContext): Unit
  def propertiesPanel: scalafx.scene.Node
}