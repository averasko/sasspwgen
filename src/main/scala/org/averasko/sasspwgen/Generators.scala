package org.averasko.sasspwgen

import scala.collection.mutable.ArrayBuffer

object Generators {
  // TODO: I don't like List here as it might be taking too much memory -- replace with Seq(?)

//  def permute(l : List[String]) : List[List[String]] = {
//  }

  def allPermuts(n : Int) : Seq[ArrayBuffer[Int]] = {
    val firstPermut = (0 to n - 1).to[scala.collection.mutable.ArrayBuffer]


    val allPermutCount = factorial(n)

    Seq.iterate(firstPermut, allPermutCount - 1)(nextPermut)
  }

  // TODO: looks like I MUST have output IMMUTABLE to use it in Seq later
  def nextPermut(permut : ArrayBuffer[Int]) : ArrayBuffer[Int] = {
    val k = lastIncValueIdx(permut.toVector)
    if (k < 0) throw new IllegalStateException("ups!") else {
      val l = lastIncValueIdx(permut.toVector, k + 1)
      if (l < 0) throw new IllegalStateException("ups!") else {
        swapInPlace(permut, k, l)
        reverseInPlace(permut, k + 1, permut.length)
      }
    }
  }

  def swapInPlace(v : ArrayBuffer[Int], i1 : Int, i2 : Int) : ArrayBuffer[Int] = {
    val tmp = v(i1);
    v.update(i1, v(i2));
    v.update(i2, tmp);
    v
  }

  def reverseInPlace(v : ArrayBuffer[Int], i1 : Int, i2 : Int) : ArrayBuffer[Int] = {
    for (i <- 0 to (i2 - i1) / 2) {
      swapInPlace(v, i1 + i, i2 - i)
    }
    v
  }

  /**
    * returns the largest index k such that l[k] < l[k+1]
    * returns -1 if k does not exist
    *
    * @note Vector provides efficient means to access and modify
    */
  def lastIncValueIdx(permut : Vector[Int], startFrom : Int = 0) : Int = {
    var i = permut.length - 1 - 1
    var i2 = i + 1
    while (i >= 0 && permut(i) >= permut(i2)) {
      i = i - 1
      i2 = i + 1
    }
    i
  }

  def factorial(n : Int) : Int = n match {
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
