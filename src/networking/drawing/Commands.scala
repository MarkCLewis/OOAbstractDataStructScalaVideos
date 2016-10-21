package networking.drawing

import stackqueue.utility.RPNCalc
import scalafx.application.Platform

// echo
// add 3 4 5
// refresh
object Commands {
  private val commands = Map[String, (String, Drawing) => Any](
      "add" -> ((args, d) => args.trim.split(" +").map(_.toInt).sum),
      "echo" -> ((args, d) => args.trim),
      "refresh" -> ((args, d) => Platform.runLater(d.draw())),
      "rpn" -> ((args, d) => RPNCalc(args.trim.split(" +"), d.vars)),
      "set" -> ((args, d) => {  // set x 3
        val parts = args.trim.split(" +")
        d.setVar(parts(0), parts(1).toDouble)
        parts(0)+" = "+parts(1)
      }),
      "freeze" -> ((args, d) => Thread.sleep(args.trim.toInt * 1000))
      )
    
  def apply(input: String, drawing: Drawing): Any = {
    val spaceIndex = input.indexOf(" ")
    val (command, args) = if(spaceIndex < 0) (input, "") else input.splitAt(spaceIndex)
    if(commands.contains(command.toLowerCase())) {
      commands(command.toLowerCase())(args.trim, drawing)
    } else "Not a valid command."
  }
}