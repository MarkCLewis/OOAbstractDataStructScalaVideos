package stackqueue

trait Stack[A] {
  /**
   * This adds an element to the stack.
   */
  def push(a: A): Unit
  
  /**
   * Removes an element from the stack. The element that is removed is the
   * one that was most recently added. LIFO
   */
  def pop(): A
  
  /**
   * Gives back the next item that would be popped.
   */
  def peek: A
  
  /**
   * Tells is there are no items on the stack to pop.
   */
  def isEmpty: Boolean
}