package networking.drawing

import scalafx.Includes._
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.layout.VBox
import scalafx.scene.control.ColorPicker
import scalafx.scene.Node
import scalafx.event.ActionEvent
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class DrawText(
    val drawing: Drawing,
    private var x: Double,
    private var y: Double,
    private var text: String,
    @transient private var color: Color
    ) extends Drawable {
  
  @transient private var propPanel: Node = null
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillText(text, x, y)
  }

  def propertiesPanel: scalafx.scene.Node = {
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
      val textField = DrawingMain.labeledTextField("text", text, s => {
        text = s
        drawing.draw()
      })
      val colorPicker = new ColorPicker(color)
      colorPicker.onAction = (ae: ActionEvent) => {
        color = colorPicker.value()
        drawing.draw()
      }
      
      panel.children = List(xField, yField, textField, colorPicker)
      propPanel = panel
    }
    propPanel
  }
  
  override def toString: String = "Text"
  
  def toXML: xml.Node = {
    <drawable type="Text" x={x.toString} y={y.toString}>
			<text>{text}</text>
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

object DrawText {
  def apply(d: Drawing, node: xml.Node): DrawText = {
    val x = (node \ "@x").text.toDouble
    val y = (node \ "@y").text.toDouble
    val text = (node \ "text").text
    val color = Drawable.xmlToColor((node \ "color").head)
    new DrawText(d, x, y, text, color)
  }
}