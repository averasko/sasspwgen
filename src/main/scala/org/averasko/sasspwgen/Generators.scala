package org.averasko.sasspwgen

object Generators {
  //def permute(l : List[String]) : List[List[String]] = {}

  def numbers(maxLength : Int) : Seq[Int] = 0 to Math.round(Math.pow(10.0f, maxLength).toFloat) - 1

}
