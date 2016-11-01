package priorityqueue

class UnsortedLLPQ[A](higherP: (A, A) => Boolean) extends PriorityQueue[A] {
  private var default: A = _
  private class Node(val data: A, var prev: Node, var next: Node)
  private val end = new Node(default, null, null)
  end.next = end
  end.prev = end

  // O(1)
  def enqueue(a: A): Unit = {
    val newNode = new Node(a, end.prev, end)
    end.prev.next = newNode
    end.prev = newNode
  }

  // O(n)
  def dequeue(): A = {
    val hpn = findHighestPriorityNode()
    hpn.prev.next = hpn.next
    hpn.next.prev = hpn.prev
    hpn.data
  }

  // O(n)
  def peek: A = findHighestPriorityNode().data

  // O(1)
  def isEmpty: Boolean = end.next == end

  private def findHighestPriorityNode(): Node = {
    var ret = end.next
    var rover = ret.next
    while (rover != end) {
      if (higherP(rover.data, ret.data)) ret = rover
      rover = rover.next
    }
    ret
  }
}