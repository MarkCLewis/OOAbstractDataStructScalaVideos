package networking.drawing

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

trait Drawable extends Serializable {
  val drawing: Drawing
  def draw(gc: GraphicsContext): Unit
  def propertiesPanel: scalafx.scene.Node
  def toXML: xml.Node
}

object Drawable {
  def apply(d: Drawing, node: xml.Node): Drawable = {
    (node \ "@type").text match {
      case "Transform" => DrawTransform(d, node)
      case "Rectangle" => DrawRectangle(d, node)
      case "Text" => DrawText(d, node)
      case "Maze" => DrawMaze(d, node)
    }
  }
  
  def colorToXML(color: Color): xml.Node = {
    <color red={color.red.toString} green={color.green.toString} blue={color.blue.toString} opacity={color.opacity.toString}/>
  }
  
  def xmlToColor(node: xml.Node): Color = {
    val red = (node \ "@red").text.toDouble
    val green = (node \ "@green").text.toDouble
    val blue = (node \ "@blue").text.toDouble
    val opacity = (node \ "@opacity").text.toDouble
    Color(red, green, blue, opacity)
  }
}