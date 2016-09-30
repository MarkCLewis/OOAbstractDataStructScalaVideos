package iostream

import java.io.ObjectOutputStream
import java.io.ObjectInputStream

class OtherData(val id: String, val major: String)
class Student(val name: String, 
    val grades: Array[Int], 
    @transient private var _od: OtherData) extends Serializable {
  def od: OtherData = {
    if(_od == null) {
      _od = new OtherData("", "")
    }
    _od
  }
  private def writeObject(oos: ObjectOutputStream): Unit = {
    oos.defaultWriteObject()
    oos.writeUTF(_od.id)
    oos.writeUTF(_od.major)
  }
  private def readObject(ois: ObjectInputStream): Unit = {
    ois.defaultReadObject()
    _od = new OtherData(ois.readUTF(), ois.readUTF())
  }
}

object SerializeStudent extends App {
  import LoanPattern._
  
  val s = new Student("Jim", Array(67,97,23,95,86), new OtherData("1234567","CS"))
  withOOS("student.bin") { oos => 
    oos.writeObject(s)
  }
  
  val s2 = withOIS("student.bin") { ois =>
    ois.readObject() match {
      case s:Student => s
      case _ => 
        println("Not a student")
        throw new RuntimeException("File didn't contain a student.")
    }
  }
  println(s2.name)
  println(s2.od.id)
}