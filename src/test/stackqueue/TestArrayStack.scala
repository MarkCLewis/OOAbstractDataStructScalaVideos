package test.stackqueue

import stackqueue.ArrayStack
import stackqueue.Stack
import org.junit._
import org.junit.Assert._

class TestArrayStack {
  private var stack: Stack[Int] = null
  
  @Before def makeStack {
    stack = new ArrayStack[Int]()
  }
  
  @Test def emptyOnCreate {
    assertTrue(stack.isEmpty)
  }
  
  @Test def nonEmptyOnPush {
    stack.push(1)
    assertFalse(stack.isEmpty)
  }
  
  @Test def pushPop1 {
    stack.push(42)
    assertEquals(42, stack.peek)
    assertEquals(42, stack.pop())
  }
  
  @Test def pushPopPushPop {
    stack.push(5)
    assertFalse(stack.isEmpty)
    assertEquals(5, stack.peek)
    assertEquals(5, stack.pop())
    assertTrue(stack.isEmpty)
    stack.push(55)
    assertFalse(stack.isEmpty)
    assertEquals(55, stack.peek)
    assertEquals(55, stack.pop())
    assertTrue(stack.isEmpty)
  }
  
  @Test def pushPushPopPop {
    stack.push(5)
    stack.push(55)
    assertFalse(stack.isEmpty)
    assertEquals(55, stack.peek)
    assertEquals(55, stack.pop())
    assertFalse(stack.isEmpty)
    assertEquals(5, stack.peek)
    assertEquals(5, stack.pop())
    assertTrue(stack.isEmpty)
  }
  
  @Test def pushPop100 {
    val nums = Array.fill(100)(util.Random.nextInt())
    nums.foreach(stack.push)
    nums.reverse.foreach { n =>
      assertEquals(n, stack.peek)
      assertEquals(n, stack.pop())
    }
  }
}