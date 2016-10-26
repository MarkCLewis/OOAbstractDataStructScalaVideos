package test.linkedlist

import org.junit._
import org.junit.Assert._
import linkedlist.SinglyLinkedList

class TestSinglyLinkedList {
  var lst: SinglyLinkedList[Int] = null
  
  @Before def makeList = {
    lst = new SinglyLinkedList[Int]()
  }
  
  @Test def addOne: Unit = {
    lst.insert(0, 93)
    assertEquals(93, lst(0))
  }

  @Test def addTwo: Unit = {
    lst.insert(0, 93)
    lst.insert(1, 94)
    assertEquals(93, lst(0))
    assertEquals(94, lst(1))
  }

  @Test def addTwoAndUpdate: Unit = {
    lst.insert(0, 93)
    lst.insert(1, 94)
    lst(0) = 5
    lst(1) = 6
    assertEquals(5, lst(0))
    assertEquals(6, lst(1))
  }
  
  @Test def removeTest: Unit = {
    lst.insert(0, 5)
    lst.insert(0, 4)
    lst.insert(0, 3)
    lst.insert(0, 2)
    lst.insert(0, 1)
    assertEquals(3, lst.remove(2))
    assertEquals(1, lst(0))
    assertEquals(2, lst(1))
    assertEquals(4, lst(2))
    assertEquals(5, lst(3))
  }
}