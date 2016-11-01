package priorityqueue

trait PriorityQueue[A] {
  /**
   * Adds an item to the queue.
   */
  def enqueue(a: A): Unit
  
  /**
   * Removes the item with the highest priority.
   */
  def dequeue(): A
  
  /**
   * Gives back the next item that would be dequeued.
   */
  def peek: A
  
  /**
   * Tells is there are no items on the queue to dequeue.
   */
  def isEmpty: Boolean
}