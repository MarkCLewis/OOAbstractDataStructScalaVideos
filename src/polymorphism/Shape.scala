package polymorphism

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

abstract class Shape(val color: Color) {
  def area: Double
  def perimeter: Double
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
  }
}

object Shape {
  def main(args: Array[String]): Unit = {
    val rect = new Rectangle(3,4, Color.Black)
    printShapeData(rect)
    val circle = new Circle(3, Color.Black)
    printShapeData(circle)
    val square = new Square(5, Color.Black)
    printShapeData(square)
    val square2 = new MutableSquare(5, Color.Black)
    square2.width = 99
  }
  
  def printShapeData(s: Shape): Unit = {
    println(s"Area = ${s.area}")
    println(s"Perimeter = ${s.perimeter}")
  }
}