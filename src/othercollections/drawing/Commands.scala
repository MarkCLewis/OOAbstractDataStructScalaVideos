package othercollections.drawing

// echo
// add 3 4 5
// refresh
object Commands {
  private val commands = Map[String, (String, Drawing) => Any](
      "add" -> ((args, d) => args.trim.split(" +").map(_.toInt).sum),
      "echo" -> ((args, d) => args.trim),
      "refresh" -> ((args, d) => d.draw())
      )
    
  def apply(input: String, drawing: Drawing): Any = {
    val spaceIndex = input.indexOf(" ")
    val (command, args) = if(spaceIndex < 0) (input, "") else input.splitAt(spaceIndex)
    if(commands.contains(command.toLowerCase())) {
      commands(command.toLowerCase())(args.trim, drawing)
    } else "Not a valid command."
  }
}