package iostream.drawing

import scalafx.Includes._
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.Node
import scalafx.scene.layout.VBox
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TextField
import scalafx.event.ActionEvent
import scalafx.scene.control.ColorPicker
import java.io.ObjectOutputStream
import java.io.ObjectInputStream

class DrawRectangle(
    val drawing: Drawing,
    private var x: Double,
    private var y: Double,
    private var width: Double,
    private var height: Double,
    @transient private var color: Color
    ) extends Drawable {
  
  @transient private var propPanel: Node = null
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillRect(x, y, width, height)
  }

  def propertiesPanel: Node = {
    if(propPanel == null) {
      val panel = new VBox
      val xField = DrawingMain.labeledTextField("x", x.toString(), s => {
        x = s.toDouble
        drawing.draw()
      })
      val yField = DrawingMain.labeledTextField("y", y.toString(), s => {
        y = s.toDouble
        drawing.draw()
      })
      val widthField = DrawingMain.labeledTextField("width", width.toString(), s => {
        width = s.toDouble
        drawing.draw()
      })
      val heightField = DrawingMain.labeledTextField("height", height.toString(), s => {
        height = s.toDouble
        drawing.draw()
      })
      val colorPicker = new ColorPicker(color)
      colorPicker.onAction = (ae: ActionEvent) => {
        color = colorPicker.value()
        drawing.draw()
      }
      
      panel.children = List(xField, yField, widthField, heightField, colorPicker)
      propPanel = panel
    }
    propPanel
  }
  
  override def toString: String = "Rectangle"
  
  def toXML: xml.Node = {
    <drawable type="Rectangle" x={x.toString} y={y.toString} width={width.toString} height={height.toString}>
			{Drawable.colorToXML(color)}
		</drawable>
  }
  
  private def writeObject(oos: ObjectOutputStream): Unit = {
    oos.defaultWriteObject()
    oos.writeDouble(color.red)
    oos.writeDouble(color.green)
    oos.writeDouble(color.blue)
    oos.writeDouble(color.opacity)
  }
  
  private def readObject(ois: ObjectInputStream): Unit = {
    ois.defaultReadObject()
    color = Color(ois.readDouble(),ois.readDouble(),ois.readDouble(),ois.readDouble())
  }
}

object DrawRectangle {
  def apply(d: Drawing, node: xml.Node): DrawRectangle = {
    val x = (node \ "@x").text.toDouble
    val y = (node \ "@y").text.toDouble
    val width = (node \ "@width").text.toDouble
    val height = (node \ "@height").text.toDouble
    val color = Drawable.xmlToColor((node \ "color").head)
    new DrawRectangle(d, x, y, width, height, color)
  }
}