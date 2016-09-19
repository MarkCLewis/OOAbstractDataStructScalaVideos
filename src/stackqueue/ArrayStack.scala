package stackqueue

import scala.reflect.ClassTag

class ArrayStack[A: ClassTag] extends Stack[A] {
  private var data = new Array[A](10)
  private var top = 0
  
  def push(a: A): Unit = {
    if(top >= data.length) {
      val tmp = new Array[A](data.length*2)
      Array.copy(data, 0, tmp, 0, data.length)
      data = tmp
    }
    data(top) = a
    top += 1
  }
  
  def pop(): A = {
    top -= 1
    data(top)
  }
  
  def peek: A = data(top-1)
  
  def isEmpty: Boolean = top == 0
}