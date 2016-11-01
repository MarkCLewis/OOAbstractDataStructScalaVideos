package linkedlist

import scala.collection.immutable.LinearSeq

sealed trait ImmutableSLList[+A] extends LinearSeq[A] {
  def ::[B >: A](elem: B): ImmutableSLList[B] = new Cons(elem, this)
  
  override def iterator = new Iterator[A] {
    var rover: LinearSeq[A] = ImmutableSLList.this
    def hasNext = !rover.isEmpty
    def next: A = {
      val ret = rover.head
      rover = rover.tail
      ret
    }
  }
}

final class Cons[A](override val head: A, override val tail: ImmutableSLList[A]) extends ImmutableSLList[A] {
  def length: Int = 1 + tail.length
  def apply(index: Int): A = if (index == 0) head else tail(index - 1)
  override def isEmpty = false
}

object MyNil extends ImmutableSLList[Nothing] {
  def length = 0
  def apply(index: Int) = throw new IllegalArgumentException("Can't index Nil")
  override def isEmpty = true
  override def head = throw new IllegalArgumentException("Can't get the head of Nil")
  override def tail = throw new IllegalArgumentException("Can't get the tail of Nil")
}