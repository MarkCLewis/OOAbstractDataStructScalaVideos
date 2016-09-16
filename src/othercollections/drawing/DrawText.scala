package othercollections.drawing

import scalafx.Includes._
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.layout.VBox
import scalafx.scene.control.ColorPicker
import scalafx.scene.Node
import scalafx.event.ActionEvent

class DrawText(
    val drawing: Drawing,
    private var x: Double,
    private var y: Double,
    private var text: String,
    private var color: Color
    ) extends Drawable {
  
  private var propPanel: Option[Node] = None
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillText(text, x, y)
  }

  def propertiesPanel: scalafx.scene.Node = {
    if(propPanel.isEmpty) {
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
      propPanel = Some(panel)
    }
    propPanel.get
  }
  
  override def toString: String = "Text"
}