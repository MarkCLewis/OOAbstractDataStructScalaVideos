package networking

import java.net.Socket
import java.io.PrintStream
import java.io.InputStreamReader
import java.io.BufferedReader
import io.StdIn._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ChatClient extends App {
  val sock = new Socket("localhost", 4000)
  val in = new BufferedReader(new InputStreamReader(sock.getInputStream))
  val out = new PrintStream(sock.getOutputStream)
  var stopped = false
  Future {
    while (!stopped) {
      val p = in.readLine()
      if (p != null) println(p)
    }
  }
  var input = ""
  while (input != ":quit") {
    val input = readLine
    out.println(input)
  }
  sock.close()
  stopped = true
}