package guisgraphics.drawing

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

class DrawRectangle(
    val drawing: Drawing,
    private var x: Double,
    private var y: Double,
    private var width: Double,
    private var height: Double,
    private var color: Color
    ) extends Drawable {
  
  private var propPanel: Option[Node] = None
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillRect(x, y, width, height)
  }

  def propertiesPanel: Node = {
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
      propPanel = Some(panel)
    }
    propPanel.get
  }
  
  override def toString: String = "Rectangle" 
}