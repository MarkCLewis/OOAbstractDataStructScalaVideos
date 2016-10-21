package networking.drawing

import scalafx.Includes._
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.Node
import scalafx.scene.layout.VBox
import scalafx.scene.control.ComboBox
import scalafx.event.ActionEvent
import collection.mutable

class DrawTransform(val drawing: Drawing,
  private val _children:mutable.Buffer[Drawable] = mutable.Buffer[Drawable](),
  private var transformType:DrawTransform.Value = DrawTransform.Translate,
  private var value1: Double = 0.0,
  private var value2: Double = 0.0) extends Drawable {
  @transient private var propPanel: Node = null

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
    if(propPanel == null) {
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
      propPanel = panel
    }
    propPanel
  }
  override def toString: String = "Transform" 
  def toXML: xml.Node = {
    <drawable type="Transform" transType={transformType.toString} value1={value1.toString()} value2={value2.toString()}>
			{_children.map(_.toXML)}
		</drawable>
  }
}

object DrawTransform extends Enumeration {
  val Rotate, Scale, Shear, Translate = Value
  
  def apply(d: Drawing, node: xml.Node): DrawTransform = {
    val children = (node \ "drawable").map(n => Drawable(d, n)).toBuffer
    val typeString = (node \ "@transType").text
    val transType = DrawTransform.values.find(_.toString == typeString).get
    val v1 = (node \ "@value1").text.toDouble
    val v2 = (node \ "@value2").text.toDouble
    new DrawTransform(d, children, transType, v1, v2)
  }
}