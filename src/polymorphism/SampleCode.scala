package polymorphism

import io.StdIn._

object SampleCode {
  def square(x: Double) = x * x

  def sumValues(n: Int): Double = {
    if (n < 1) 0.0 else readDouble() + sumValues(n - 1)
  }

  def productValues(n: Int): Double = {
    if (n < 1) 1.0 else readDouble() * productValues(n - 1)
  }

  def combineValues(n: Int, base: Double, op: (Double, Double) => Double): Double = {
    if (n < 1) base else op(readDouble(), combineValues(n - 1, base, op))
  }
}