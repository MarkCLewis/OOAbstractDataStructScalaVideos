package iostream

object WriteBinaryArrays extends App {
  import LoanPattern._
  
  val arr = Array.fill(10)(math.random)
  withDOS("array.bin"){ dos => 
    dos.writeInt(arr.length)
    arr.foreach(dos.writeDouble)
  }
  
  val arr2 = withDIS("array.bin"){ dis =>
    Array.fill(dis.readInt())(dis.readDouble())
  }
  println(arr.mkString(", "))
  println(arr2.mkString(", "))
}