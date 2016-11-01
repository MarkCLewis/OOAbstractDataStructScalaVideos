package linkedlist

import stackqueue.Queue

class ListQueue[A] extends Queue[A] {
  private class Node(val data: A, var next: Node)
  private var front: Node = null
  private var back: Node = null

  def enqueue(a: A): Unit = {
    if(isEmpty) {
      front = new Node(a, null)
      back = front
    } else {
      back.next = new Node(a, null)
      back = back.next
    }
  }
  
  def dequeue(): A = {
    val ret = front.data
    front = front.next
    if (front == null) back = null
    ret
  }

  def peek: A = front.data

  def isEmpty: Boolean = front == null
}