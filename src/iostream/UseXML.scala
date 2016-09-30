package iostream

object UseXML extends App {
  val xdata = xml.XML.loadFile("drawing.xml")
  val root = xdata \ "drawable"
  val value1 = (root \ "@value1").text.toDouble
  println(root)
  println(value1)
  println((root \ "@x").mkString(","))
  println((root \\ "@x").mkString(","))
}