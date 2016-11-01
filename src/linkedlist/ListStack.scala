package linkedlist

import stackqueue.Stack

class ListStack[A] extends Stack[A] {
  private case class Node(data: A, next: Node)
  private var top: Node = null
  
  def push(a: A): Unit = {
    top = new Node(a, top)
  }
  
  def pop(): A = {
    val ret = top.data
    top = top.next
    ret
  }
  
  def peek: A = top.data
  
  def isEmpty: Boolean = top == null
}