package oobasics

object GradeBook {
  def main(args: Array[String]): Unit = {
    val students = List(new Student("Jane", "Doe"), new Student("John", "Doe"))
    for (s <- students) printStudent(s)
  }

  def printStudent(s: Student): Unit = {
    println(s.firstName + " " + s.lastName)
    println("Grade = " + s.average)
    println("Tests = " + s.tests.mkString(", "))
  }
}