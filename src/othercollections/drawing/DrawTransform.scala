package othercollections.drawing

import scalafx.Includes._
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.Node
import scalafx.scene.layout.VBox
import scalafx.scene.control.ComboBox
import scalafx.event.ActionEvent
import collection.mutable

class DrawTransform(val drawing: Drawing) extends Drawable {
  private val _children = mutable.Buffer[Drawable]()
  private var propPanel: Option[Node] = None
  private var transformType = DrawTransform.Translate
  private var value1 = 0.0
  private var value2 = 0.0

  def children = _children.map(i => i)
  def addChild(d: Drawable): Unit = _children += d
  def draw(gc: GraphicsContext): Unit = {
    gc.save()
    transformType match {
      case DrawTransform.Rotate => gc.rotate(value1)
      case DrawTransform.Scale => gc.scale(value1, value2)
      case DrawTransform.Shear => gc.transform(1.0, value1, value2, 1.0, 0.0, 0.0)
      case DrawTransform.Translate => gc.translate(value1, value2)
    }
    _children.foreach { _.draw(gc) }
    gc.restore()
  }
  def propertiesPanel: scalafx.scene.Node = {
    if(propPanel.isEmpty) {
      val panel = new VBox
      val combo = new ComboBox(DrawTransform.values.toSeq)
      combo.onAction = (ae: ActionEvent) => {
        transformType = combo.selectionModel().selectedItem()
        drawing.draw()
      }
      combo.selectionModel().select(transformType)
      val v1Field = DrawingMain.labeledTextField("x/theta", value1.toString(), s => {
        value1 = s.toDouble
        drawing.draw()
      })
      val v2Field = DrawingMain.labeledTextField("y", value2.toString(), s => {
        value2 = s.toDouble
        drawing.draw()
      })
      
      panel.children = List(combo, v1Field, v2Field)
      propPanel = Some(panel)
    }
    propPanel.get
  }
  override def toString: String = "Transform" 
}

object DrawTransform extends Enumeration {
  val Rotate, Scale, Shear, Translate = Value
}