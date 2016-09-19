package test.stackqueue

import stackqueue.ArrayQueue
import stackqueue.Queue
import org.junit._
import org.junit.Assert._

class TestArrayQueue {
  private var queue: Queue[Int] = null
  
  @Before def makeQueue {
    queue = new ArrayQueue[Int]()
  }
  
  @Test def emptyOnCreate {
    assertTrue(queue.isEmpty)
  }
  
  @Test def nonEmptyOnEnqueue {
    queue.enqueue(1)
    assertFalse(queue.isEmpty)
  }
  
  @Test def enqueueDequeue1 {
    queue.enqueue(42)
    assertEquals(42, queue.peek)
    assertEquals(42, queue.dequeue())
  }
  
  @Test def enqueueDequeueEnqueueDequeue {
    queue.enqueue(5)
    assertFalse(queue.isEmpty)
    assertEquals(5, queue.peek)
    assertEquals(5, queue.dequeue())
    assertTrue(queue.isEmpty)
    queue.enqueue(55)
    assertFalse(queue.isEmpty)
    assertEquals(55, queue.peek)
    assertEquals(55, queue.dequeue())
    assertTrue(queue.isEmpty)
  }
  
  @Test def enqueueEnqueueDequeueDequeue {
    queue.enqueue(5)
    queue.enqueue(55)
    assertFalse(queue.isEmpty)
    assertEquals(5, queue.peek)
    assertEquals(5, queue.dequeue())
    assertFalse(queue.isEmpty)
    assertEquals(55, queue.peek)
    assertEquals(55, queue.dequeue())
    assertTrue(queue.isEmpty)
  }
  
  @Test def enqueueDequeue100 {
    val nums = Array.fill(100)(util.Random.nextInt())
    nums.foreach(queue.enqueue)
    nums.foreach { n =>
      assertEquals(n, queue.peek)
      assertEquals(n, queue.dequeue())
    }
  }
}