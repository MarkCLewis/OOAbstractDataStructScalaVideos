package polymorphism.themepark

class ToDValues[A] private (private val values: Array[Option[A]]) {
  
  def apply(hour: Int) = values(hour).get
  
  def get(hour: Int): Option[A] = values(hour)
  
  def update(hour: Int, v: A) = values(hour) = Some(v)
  
  def clear(hour: Int): Unit = {
    values(hour) = None
  }
  
  def combine[B, C](o: ToDValues[B])(f: (Option[A], Option[B]) => Option[C]): ToDValues[C] = {
    val ret = ToDValues[C]()
    for((v, i) <- (values, o.values).zipped.map((v1, v2) => f(v1, v2)).zipWithIndex) {
      ret.values(i) = v
    }
    ret
  }
}

object ToDValues extends App {
  val riders1 = ToDValues[Int]()
  val riders2 = ToDValues[Int]()
  val worker1 = ToDValues[String]()
  val worker2 = ToDValues[String]()
  
  riders1(12) = 5
  riders1(8) = 10
  riders1(14) = 7
  riders2(14) = 8
  
  worker1(12) = "Kyle"
  
  val totalRiders = riders1.combine(riders2)((o1, o2) => (o1, o2) match {
    case (None, None) => None
    case (Some(a), None) => Some(a)
    case (None, Some(b)) => Some(b)
    case (Some(a), Some(b)) => Some(a+b)
  })
  
  totalRiders.values.foreach(println)
  
  def apply[A](): ToDValues[A] = new ToDValues(Array.fill(24)(None))
  def apply[A](a: A*): ToDValues[A] = {
    val opts = a.map(Option(_)).toArray
    new ToDValues[A](if (opts.length < 24) opts.padTo(24, None) else if (opts.length > 24)
      opts.take(24) else opts)
  }
}