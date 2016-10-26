package linkedlist

trait ListADT[A] {
  def apply(index: Int): A
  def update(index: Int, data: A): Unit
  def insert(index: Int, data: A): Unit
  def remove(index: Int): A
}