package iostream.drawing

import scalafx.Includes._
import scalafx.scene.canvas.GraphicsContext
import stackqueue.ArrayQueue
import scalafx.scene.paint.Color
import scalafx.scene.layout.VBox
import scalafx.scene.control.Button
import scalafx.event.ActionEvent

class DrawMaze(val drawing: Drawing) extends Drawable {
  @transient private var propPanel: scalafx.scene.Node = null
  private var startX, startY = 0
  private var endX, endY = 9
  private var shortPath = List[(Int, Int)]()

  private val maze = Array(
    Array(0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 1, 0, 0, 1, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 1, 1, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 0, 1, 0, 1, 0, 1, 0),
    Array(0, 1, 1, 0, 1, 0, 1, 0, 1, 0),
    Array(0, 0, 0, 0, 0, 0, 1, 0, 1, 0),
    Array(0, 1, 1, 1, 1, 1, 1, 0, 1, 1),
    Array(0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 1, 0, 1, 1, 1, 1, 0, 1, 0),
    Array(0, 0, 0, 1, 0, 0, 0, 0, 1, 0))

  import DrawMaze._

  override def toString = "Maze"

  def draw(gc: GraphicsContext): Unit = {
    gc.fill = Color.Black
    for (i <- maze.indices; j <- maze(i).indices) {
      if (maze(i)(j) == 0) {
        gc.fill = if (shortPath.contains(i -> j)) Color.Green else Color.White
      } else {
        gc.fill = Color.Black
      }
      gc.fillRect(i * boxSize, j * boxSize, boxSize, boxSize)
    }
  }

  def propertiesPanel: scalafx.scene.Node = {
    if (propPanel == null) {
      val panel = new VBox
      val bfs = new Button("Breadth First Search")
      bfs.onAction = (ae: ActionEvent) => {
        breadthFirstSearch().foreach { lst =>
          shortPath = lst
          drawing.draw()
        }
      }
      panel.children = List(bfs)
      propPanel = panel
    }
    propPanel
  }

  def breadthFirstSearch(): Option[List[(Int, Int)]] = {
    val queue = new ArrayQueue[List[(Int, Int)]]()
    queue.enqueue(List(startX -> startY))
    var solution: Option[List[(Int, Int)]] = None
    var visited = Set[(Int, Int)](startX -> startY)
    while (!queue.isEmpty && solution.isEmpty) {
      val steps @ ((x, y) :: _) = queue.dequeue()
      for ((dx, dy) <- offsets) {
        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze(nx).length && maze(nx)(ny) == 0 && !visited(nx -> ny)) {
          if (nx == endX && ny == endY) {
            solution = Some((nx -> ny) :: steps)
          } else {
            visited += nx -> ny
            queue.enqueue((nx -> ny) :: steps)
          }
        }
      }
    }
    solution
  }

  def toXML: xml.Node = {
    <drawable type="Maze">
    </drawable>
  }
}

object DrawMaze {
  private val offsets = Vector((0, -1), (1, 0), (0, 1), (-1, 0))
  private val boxSize = 20

  def apply(d: Drawing, node: xml.Node): DrawMaze = {
    new DrawMaze(d)
  }

}