package stackqueue

trait Queue[A] {
  /**
   * Adds an item to the queue.
   */
  def enqueue(a: A): Unit
  
  /**
   * Removes the item that has been on the queue longest. FIFO
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