package guisgraphics

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import polymorphism.Vect2D
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TextField
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color

object AnimationExample extends JFXApp {
  case class Chaser(p: Vect2D)
  private var chasers = List[Chaser]()
  private var box = Vect2D(0, 0)
  private var upPressed = false
  private var downPressed = false
  private var leftPressed = false
  private var rightPressed = false

  stage = new JFXApp.PrimaryStage {
    title = "Animation"
    scene = new Scene(600, 600) {
      val border = new BorderPane
      val field = new TextField
      val canvas = new Canvas(600, 600)
      val gc = canvas.graphicsContext2D
      //      border.top = field
      border.center = canvas
      root = border

      canvas.onMouseClicked = (me: MouseEvent) => {
        chasers ::= new Chaser(Vect2D(me.x, me.y))
        canvas.requestFocus()
      }
      canvas.onMouseEntered = (me: MouseEvent) => {
        canvas.requestFocus()
      }
      canvas.onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => upPressed = true
          case KeyCode.Down => downPressed = true
          case KeyCode.Left => leftPressed = true
          case KeyCode.Right => rightPressed = true
          case _ =>
        }
      }
      canvas.onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => upPressed = false
          case KeyCode.Down => downPressed = false
          case KeyCode.Left => leftPressed = false
          case KeyCode.Right => rightPressed = false
          case _ =>
        }
      }

      var lastTime = 0L
      val boxSpeed = 40
      val chaserSpeed = 20
      val timer = AnimationTimer { time =>
        if (lastTime != 0) {
          val interval = (time - lastTime) / 1e9
          if (upPressed) box += Vect2D(0, -interval * boxSpeed)
          if (downPressed) box += Vect2D(0, interval * boxSpeed)
          if (leftPressed) box += Vect2D(-interval * boxSpeed, 0)
          if (rightPressed) box += Vect2D(interval * boxSpeed, 0)
          chasers = chasers.map { c =>
            val sep = box - c.p
            val move = sep / sep.magnitude * interval * chaserSpeed
            Chaser(c.p+move)
          }.filter { c =>
            (box - c.p).magnitude > 10
          }
        }
        lastTime = time
        gc.fill = Color.White
        gc.fillRect(0, 0, canvas.width(), canvas.height())
        gc.fill = Color.Red
        for (Chaser(p) <- chasers) {
          gc.fillOval(p.x - 5, p.y - 5, 10, 10)
        }
        gc.fill = Color.Green
        gc.fillRect(box.x - 5, box.y - 5, 10, 10)
      }
      timer.start()
      canvas.requestFocus()
    }
  }
}