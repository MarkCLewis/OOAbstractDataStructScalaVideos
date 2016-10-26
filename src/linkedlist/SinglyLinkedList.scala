package linkedlist

class SinglyLinkedList[A] extends ListADT[A] {
  private class Node(var data: A, var next: Node)
  private var head: Node = null
  
  def apply(index: Int): A = {
    require(index >= 0)
    var rover = head
    for(i <- 0 until index) rover = rover.next
    rover.data
  }
  
  def update(index: Int, data: A): Unit = {
    require(index >= 0)
    var rover = head
    for(i <- 0 until index) rover = rover.next
    rover.data = data
  }
  
  def insert(index: Int, data: A): Unit = {
    require(index >= 0)
    if(index == 0) {
      head = new Node(data, head)
    } else {
      var rover = head
      for(i <- 0 until index-1) rover = rover.next
      rover.next = new Node(data, rover.next)
    }
  }
  
  def remove(index: Int): A = {
    require(index >= 0)
    if(index == 0) {
      val ret = head.data
      head = head.next
      ret
    } else {
      var rover = head
      for(i <- 0 until index-1) rover = rover.next
      val ret = rover.next.data
      rover.next = rover.next.next
      ret
    }
  }
}