package networking.drawing

import scalafx.scene.control.TreeItem
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Drawing extends Serializable {
  private var root = new DrawTransform(this)
  @transient private var _gc = null:GraphicsContext
  private var _vars = Map[String, Double]()

  def gc = _gc
  def gc_=(g: GraphicsContext): Unit = _gc = g
  
  def vars = _vars
  def setVar(varName: String, value: Double): Unit = _vars = _vars + (varName -> value)

  def draw(): Unit = {
    if(_gc != null) { 
      gc.fill = Color.White
      gc.fillRect(0, 0, 2000, 2000)
      root.draw(gc)
    }
  }

  def makeTree(): TreeItem[Drawable] = {
    def helper(d: Drawable): TreeItem[Drawable] = d match {
      case dt: DrawTransform =>
        val item = new TreeItem(d)
        dt.children.foreach { c => item.children += helper(c) }
        item
      case _ => new TreeItem(d)
    }
    helper(root)
  }
  
  def toXML: xml.Node = {
    <drawing>
			{root.toXML}
			{_vars.map(t => <vars key={t._1} value={t._2.toString}/>)}
		</drawing>
  }
}

object Drawing {
  def apply(node: xml.Node): Drawing = {
    val d = new Drawing
    d.root = DrawTransform(d, (node \ "drawable").head)
    d._vars = (node \ "vars").map { v =>
      val key = (v \ "@key").text
      val value = (v \ "@value").text.toDouble
      key -> value
    }.toMap
    d
  }
}