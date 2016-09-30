package iostream.drawing

import scalafx.Includes._
import scalafx.scene.canvas.GraphicsContext
import stackqueue.ArrayQueue
import scalafx.scene.paint.Color
import scalafx.scene.layout.VBox
import scalafx.scene.control.Button
import scalafx.event.ActionEvent

class DrawMaze(val drawing: Drawing) extends Drawable {
  private var propPanel: Option[scalafx.scene.Node] = None
  private var startX, startY = 0
  private var endX, endY = 9
  private val offsets = Vector((0, -1), (1, 0), (0, 1), (-1, 0))
  private val boxSize = 20
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
    if (propPanel.isEmpty) {
      val panel = new VBox
      val bfs = new Button("Breadth First Search")
      bfs.onAction = (ae: ActionEvent) => {
        breadthFirstSearch().foreach { lst =>
          shortPath = lst
          drawing.draw()
        }
      }
      panel.children = List(bfs)
      propPanel = Some(panel)
    }
    propPanel.get
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
}