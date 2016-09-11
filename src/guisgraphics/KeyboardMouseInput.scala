package guisgraphics

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode

object KeyboardMouseInput extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Keyboard and Mouse Input"
    scene = new Scene(600, 600) {
      val img = new ImageView("http://www.cs.trinity.edu/~mlewis/banish.gif")
      val rect = Rectangle(0, 0, 30, 30)
      rect.fill = Color.Blue
      content = List(img, rect)
      onMouseMoved = (me: MouseEvent) => {
        rect.x = me.x - 0.5*rect.width()
        rect.y = me.y - 0.5*rect.height()
      }
      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => img.y = img.y() - 2
          case KeyCode.Down => img.y = img.y() + 2
          case KeyCode.Left => img.x = img.x() - 2
          case KeyCode.Right => img.x = img.x() + 2
          case _ =>
        }
      }
    }
  }
}