package polymorphism

object Sorting extends App {
  def bubbleSort[A <% Ordered[A]](arr: Array[A]): Unit = {
    for (i <- 0 until arr.length - 1; j <- 0 until arr.length - 1 - i) {
      if (arr(j + 1) < arr(j)) {
        val tmp = arr(j)
        arr(j) = arr(j + 1)
        arr(j + 1) = tmp
      }
    }
  }
  
  def bubbleSortComp[A](arr: Array[A])(lt: (A, A) => Boolean): Unit = {
    for (i <- 0 until arr.length - 1; j <- 0 until arr.length - 1 - i) {
      if (lt(arr(j + 1), arr(j))) {
        val tmp = arr(j)
        arr(j) = arr(j + 1)
        arr(j + 1) = tmp
      }
    }
  }
  
  val nums = Array.fill(10)(math.random)
  bubbleSort(nums)
  val ints = Array.fill(10)(util.Random.nextInt)
  bubbleSort(ints)
  
  bubbleSortComp(nums)(_ > _)
}