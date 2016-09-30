package iostream

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

object ReadBytes extends App {

  try {
    LoanPattern.useFileInputStream("src/iostream/ReadBytes.scala") { fis =>
      var byte = fis.read()
      while (byte >= 0) {
        print(byte + " ")
        byte = fis.read()
      }
      println()
    }
  } catch {
    case e: IOException =>
      println("Something else went wrong with IO.")
      e.printStackTrace()
    case e: FileNotFoundException =>
      println("The file isn't there")
      e.printStackTrace()
  }
  //  try {
  //    val fis = new FileInputStream("src/iostream/ReadBytes.scala")
  //    try {
  //      var byte = fis.read()
  //      while (byte >= 0) {
  //        print(byte + " ")
  //        byte = fis.read()
  //      }
  //      println()
  //    } catch {
  //      case e: IOException =>
  //        println("Something else went wrong with IO.")
  //        e.printStackTrace()
  //    } finally {
  //      fis.close()
  //    }
  //  } catch {
  //    case e: FileNotFoundException =>
  //      println("The file isn't there")
  //      e.printStackTrace()
  //  }
}