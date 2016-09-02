package oodetails

case class Vect2Dcc(x: Double, y: Double) {
  def +(v: Vect2Dcc) = Vect2Dcc(x + v.x, y + v.y)
  def -(v: Vect2Dcc) = Vect2Dcc(x - v.x, y - v.y)
  def *(c: Double) = Vect2Dcc(x * c, y * c)
  def /(c: Double) = Vect2Dcc(x / c, y / c)
  def unary_-() = Vect2Dcc(-x, -y)
  def magnitude = math.sqrt(x * x + y * y)
  def apply(index: Int): Double = index match {
    case 0 => x
    case 1 => y
    case _ => throw new IndexOutOfBoundsException(s"2D vector indexed at $index.")
  }
}

object Vect2Dcc {
  def main(args: Array[String]): Unit = {
    val v1 = Vect2Dcc(1, 2)
    val v2 = Vect2Dcc(2, 2)
    val v3 = v1 + v2
    val v4 = -v1
    println(v3.magnitude)
    println(v3)
    v3.copy(y = 99)
  }
}