package org.averasko.sasspwgen

object Generators {
  // TODO: I don't like List here as it might be taking too much memory -- replace with Seq(?)

//  def permute(l : List[String]) : List[List[String]] = {
//  }

//  def allPermuts(n : Int) : Seq[List[Int]] = {
//    val firstPermut = (0 to n - 1).toList
//    val allPermutCount = factorial(n)
//
//
//    Seq.iterate(firstPermut, allPermutCount)(nextPermut)
//  }
//
//  def nextPermut(permut : List[Int]) : List[Int] = {
//
//  }

  def findLastIncreaseIdx(l : List[Int]) : Int = {
    def rec(resIdx: Int, idx : Int, l : List[Int]) : Int = l match {
      case List() => resIdx
      case List(x) => resIdx
      case h :: t => if (h < t.head) rec(idx, idx + 1, t) else rec(resIdx, idx + 1, t)
    }

    rec(-1, 0, l)
  }

  def factorial(n : Int) : Long = n match {
    case 0 => 1
    case n => n * factorial(n - 1)
  }

  def numbers(maxLength : Int) : Seq[Int] = 0 to Math.round(Math.pow(10.0f, maxLength).toFloat) - 1

  def years : Seq[String] = (1950 to 2030).map(Integer.toString)
  def months : Seq[String] = (1 to 12).map(Integer.toString)
  //TODO: add leading zeroes

  def days : Seq[String] = (1 to 31).map(Integer.toString)
  //TODO: add leading zeroes


}
