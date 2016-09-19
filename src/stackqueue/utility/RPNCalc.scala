package stackqueue.utility

import stackqueue.ArrayStack

object RPNCalc {
  // 2+3*5
  // 3 5 * 2 +
  // 2 3 - 5 *
  def apply(args: Seq[String], vars: Map[String, Double]): Double = {
    val stack = new ArrayStack[Double]()
    for(arg <- args; if arg.nonEmpty) arg match {
      case "+" => stack.push(stack.pop() + stack.pop())
      case "*" => stack.push(stack.pop() * stack.pop())
      case "-" =>
        val tmp = stack.pop()
        stack.push(stack.pop() - tmp)
      case "/" =>
        val tmp = stack.pop()
        stack.push(stack.pop() / tmp)
      case "sin" => stack.push(math.sin(stack.pop()))
      case "cos" => stack.push(math.cos(stack.pop()))
      case "tan" => stack.push(math.tan(stack.pop()))
      case "sqrt" => stack.push(math.sqrt(stack.pop()))
      case x =>
        try {
          stack.push(x.toDouble)
        } catch {
          case nfe: NumberFormatException =>
            stack.push(vars(x))
        }
    }
    stack.pop()
  }
}