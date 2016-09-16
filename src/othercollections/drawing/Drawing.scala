package othercollections.drawing

import scalafx.scene.control.TreeItem
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Drawing {
  val root = new DrawTransform(this)
  private var _gc = None: Option[GraphicsContext]

  def gc = _gc
  def gc_=(g: GraphicsContext): Unit = _gc = Some(g)

  def draw(): Unit = {
    _gc.foreach { g =>
      g.fill = Color.White
      g.fillRect(0, 0, 2000, 2000)
      root.draw(g)
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
}