package org.averasko.sasspwgen

object Generators {







  def numbers(maxLength : Int) : Seq[Int] = 0 to Math.round(Math.pow(10.0f, maxLength).toFloat) - 1

  def years : Seq[String] = (1950 to 2030).map(Integer.toString)
  def months : Seq[String] = (1 to 12).map(Integer.toString)
  //TODO: add leading zeroes

  def days : Seq[String] = (1 to 31).map(Integer.toString)
  //TODO: add leading zeroes


}
