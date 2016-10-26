package linkedlist

import collection.mutable
import scala.collection.generic.GenericTraversableTemplate
import scala.collection.generic.GenericCompanion
import scala.collection.generic.SeqFactory
import scala.collection.generic.CanBuildFrom

class MutableSLList2[A] extends mutable.Buffer[A]
    with GenericTraversableTemplate[A, MutableSLList2]
    with mutable.BufferLike[A, MutableSLList2[A]]
    with mutable.Builder[A, MutableSLList2[A]] {
  private class Node(var data: A, var next: Node)
  private var hd: Node = null
  private var tl: Node = null
  private var numElems = 0
  
  override def companion: GenericCompanion[MutableSLList2] = MutableSLList2
  
  def result() = this

  def +=(elem: A): MutableSLList2.this.type = {
    if (tl == null) {
      hd = new Node(elem, null)
      tl = hd
    } else {
      tl.next = new Node(elem, null)
      tl = tl.next
    }
    numElems += 1
    this
  }

  def +=:(elem: A): MutableSLList2.this.type = {
    hd = new Node(elem, hd)
    if (tl == null) {
      tl = hd
    }
    numElems += 1
    this
  }

  def apply(n: Int): A = {
    require(n >= 0 && n < numElems)
    var rover = hd
    for (i <- 1 to n) rover = rover.next
    rover.data
  }

  def clear(): Unit = {
    hd = null
    tl = null
    numElems = 0
  }

  def insertAll(n: Int, elems: collection.Traversable[A]): Unit = {
    require(n >= 0 && n < numElems + 1)
    if (elems.nonEmpty) {
      val first = new Node(elems.head, null)
      var last = first
      numElems += 1
      for (e <- elems.tail) {
        last.next = new Node(e, null)
        last = last.next
        numElems += 1
      }
      if (n == 0) {
        last.next = hd
        hd = first
        if (tl == null) tl = last
      } else {
        var rover = hd
        for (i <- 1 until n) rover = rover.next
        last.next = rover.next
        rover.next = first
        if (last.next == null) tl = last
      }
    }
  }

  def iterator: Iterator[A] = new Iterator[A] {
    var rover = hd
    def hasNext: Boolean = rover != null
    def next(): A = {
      val ret = rover.data
      rover = rover.next
      ret
    }
  }

  def length: Int = numElems

  def remove(n: Int): A = {
    require(n >= 0 && n < numElems)
    numElems -= 1
    if (n == 0) {
      val ret = hd.data
      hd = hd.next
      if (hd == null) tl = null
      ret
    } else {
      var rover = hd
      for (i <- 1 until n) rover = rover.next
      val ret = rover.next.data
      rover.next = rover.next.next
      if (rover.next == null) tl = rover
      ret
    }
  }

  def update(n: Int, newelem: A): Unit = {
    require(n >= 0 && n < numElems)
    var rover = hd
    for (i <- 1 to n) rover = rover.next
    rover.data = newelem
  }
}

object MutableSLList2 extends SeqFactory[MutableSLList2] {
  implicit def canBuildFrom[A]: CanBuildFrom[MutableSLList2[_], A, MutableSLList2[A]] = new GenericCanBuildFrom[A]
  def newBuilder[A]: mutable.Builder[A, MutableSLList2[A]] = new MutableSLList2[A]
}