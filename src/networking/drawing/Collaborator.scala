package networking.drawing

import scalafx.Includes._
import scalafx.scene.control.TextField
import scalafx.scene.control.TextArea
import scalafx.event.ActionEvent
import scalafx.scene.control.ColorPicker
import scalafx.scene.paint.Color
import scalafx.scene.canvas.Canvas
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.FlowPane
import scalafx.scene.control.RadioButton
import scalafx.scene.control.Button
import scalafx.scene.control.ToggleGroup
import java.rmi.server.UnicastRemoteObject
import collection.mutable
import scalafx.scene.input.MouseEvent
import java.rmi.RemoteException

@remote trait RemoteCollaborator {
  def chat(text: String): Unit
  def requestSketch(): Seq[Sketchable]
  def updateSketch(who: RemoteCollaborator, sketch: Seq[Sketchable]): Unit
}

class Collaborator(server: RemoteCollaborationServer) extends UnicastRemoteObject with RemoteCollaborator {
  private val sketch = mutable.Buffer[Sketchable]()
  
  private val sketches = {
    val collabs = server.joinCollaboration(this)
    mutable.Map(collabs.map { c =>
      try {
        Some(c -> c.requestSketch())
      } catch {
        case ex: RemoteException => None
      }
    }.filter(_.nonEmpty).map(_.get):_*)
  }

  // Chat elements
  private val chatField = new TextField
  private val chatArea = new TextArea
  chatArea.editable = false
  chatField.onAction = (ae: ActionEvent) => {
    foreachCollaborator { c => c.chat(chatField.text()) }
    chatField.text = ""
  }

  // Sketching Controls
  val colorPicker = new ColorPicker(Color.Black)
  private var sketchCreator: MouseEvent => Sketchable = (me: MouseEvent) => {
    val c = colorPicker.value()
    new SketchPath(me.x, me.y, c.red, c.green, c.blue, c.opacity)
  }
  val sketchCanvas = new Canvas(2000, 2000)
  val sketchGC = sketchCanvas.graphicsContext2D
  sketchCanvas.onMousePressed = (me: MouseEvent) => {
    sketch += sketchCreator(me)
  }
  sketchCanvas.onMouseDragged = (me: MouseEvent) => {
    sketch.last.mouseEvent(me.x, me.y)
    redrawSketch()
  }
  sketchCanvas.onMouseReleased = (me: MouseEvent) => {
    sketch.last.mouseEvent(me.x, me.y)
    redrawSketch()
    foreachCollaborator(_.updateSketch(this, sketch))
  }

  sketchUpdated()
  redrawSketch()

  // Setup the window
  val stage = new Stage {
    title = "Collaborative Sketch"
    scene = new Scene(800, 600) {
      val chatBorder = new BorderPane
      chatBorder.top = chatField
      val chatScroll = new ScrollPane
      chatScroll.content = chatArea
      chatArea.prefWidth <== chatScroll.width
      chatBorder.center = chatScroll
      val sketchBorder = new BorderPane
      val canvasScroll = new ScrollPane
      canvasScroll.content = sketchCanvas
      sketchBorder.center = canvasScroll
      sketchBorder.bottom = chatBorder
      val flowPane = new FlowPane
      val buttonGroup = new ToggleGroup
      val freeformButton = new RadioButton("Freeform")
      freeformButton.selected = true
      freeformButton.onAction = (ae: ActionEvent) => {
        sketchCreator = (me: MouseEvent) => {
          val c = colorPicker.value()
          new SketchPath(me.x, me.y, c.red, c.green, c.blue, c.opacity)
        }
      }
      val lineButton = new RadioButton("Line")
      lineButton.selected = false
      lineButton.onAction = (ae: ActionEvent) => {
        sketchCreator = (me: MouseEvent) => {
          val c = colorPicker.value()
          new SketchLine(me.x, me.y, c.red, c.green, c.blue, c.opacity)
        }
      }
      val rectButton = new RadioButton("Rectangle")
      rectButton.selected = false
      rectButton.onAction = (ae: ActionEvent) => {
        sketchCreator = (me: MouseEvent) => {
          val c = colorPicker.value()
          new SketchRectangle(me.x, me.y, c.red, c.green, c.blue, c.opacity)
        }
      }
      val ellipseButton = new RadioButton("Ellipse")
      ellipseButton.selected = false
      ellipseButton.onAction = (ae: ActionEvent) => {
        sketchCreator = (me: MouseEvent) => {
          val c = colorPicker.value()
          new SketchEllipse(me.x, me.y, c.red, c.green, c.blue, c.opacity)
        }
      }
      val clearButton = new Button("Clear")
      clearButton.onAction = (ae: ActionEvent) => {
        sketch.clear()
        redrawSketch()
        foreachCollaborator { _.updateSketch(Collaborator.this, sketch) }
      }
      flowPane.children = List(freeformButton, lineButton, rectButton, ellipseButton, clearButton, colorPicker)
      buttonGroup.toggles = List(freeformButton, lineButton, rectButton, ellipseButton)
      sketchBorder.top = flowPane
      root = sketchBorder
    }
  }
  
  private def foreachCollaborator(f: RemoteCollaborator => Unit): Unit = {
    for(c <- sketches.keys) try {
      f(c)
    } catch {
      case ex: RemoteException =>
    }
  }

  def sketchUpdated(): Unit = {
    foreachCollaborator { _.updateSketch(Collaborator.this, sketch) }
  }

  def redrawSketch(): Unit = {
    sketchGC.fill = Color.White
    sketchGC.fillRect(0, 0, sketchCanvas.width(), sketchCanvas.height())
    for((_, s) <- sketches) {
      s.foreach(_.draw(sketchGC))
    }
    sketch.foreach(_.draw(sketchGC))
  }

  def chat(text: String): Unit = {
    chatArea.appendText("\n"+text)
  }

  def requestSketch(): Seq[Sketchable] = {
    sketch
  }

  def updateSketch(who: RemoteCollaborator, sketch: Seq[Sketchable]): Unit = {
    sketches(who) = sketch
    redrawSketch()
  }

}