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
    * Steinhaus–Johnson–Trotter algorithm
    */
  def permuts(n : Int): Seq[Vector[Int]] = {
    //TODO: can we make control vector to be mutable? or not?
    def rec(permut: Vector[Int], control: Vector[Int]) : Seq[Vector[Int]] = {
      val (newPermut, newControl) = nextStep(permut, control)
      if (newPermut.length > 0) {
        Seq(permut) ++ rec(newPermut, newControl)
      } else {
        Seq(permut)
      }
    }

    val firstPermut = (0 to n - 1).map((x : Int) => x).toVector
    val firstControl = Vector(0) ++ (1 to n - 1).map((_) => -1).toVector

    rec(firstPermut, firstControl)
  }

  def nextStep(permut : Vector[Int], control : Vector[Int]) : (Vector[Int], Vector[Int]) = {
    val idx = idxOfLargestWithNonZeroControl(permut, control)
    if (idx < 0) {
      //end of algorithm
      (Vector(), Vector())
    } else if (control(idx) == -1) {
      var newPermut = swap(permut, idx - 1, idx)
      var newControl = swap(control, idx - 1, idx)
      if (idx - 1 <= 0 || newPermut(idx - 2) > newPermut(idx - 1)) {
        newControl = newControl.updated(idx - 1, 0)
      }
      newControl = updateControl(newPermut, newControl, idx - 1)
      (newPermut, newControl)
    } else if (control(idx) == 1) {
      var newPermut = swap(permut, idx, idx + 1)
      var newControl = swap(control, idx, idx + 1)
      if (idx + 1 >= newPermut.length - 1 || newPermut(idx + 1) < newPermut(idx + 2)) {
        newControl = newControl.updated(idx + 1, 0)
      }
      newControl = updateControl(newPermut, newControl, idx + 1)
      (newPermut, newControl)
    } else if (control(idx) == 0) {
      throw new IllegalStateException("ups!")
    } else {
      throw new IllegalStateException("ups!")
    }
  }

  def idxOfLargestWithNonZeroControl(permut: Vector[Int], control: Vector[Int]) : Int = {
    // todo: functionalize
    var p = -1;
    for (i <- 0 until permut.length - 1) {
      if (control(i) != 0 && permut(i) > p) {
        p = permut(i)
      }
    }
    p
  }

  def swap(v: Vector[Int], i1: Int, i2: Int): Vector[Int] = {
    var tmp = v(i1)
    var res = v.updated(i1, v(i2))
    res.updated(i2, tmp)
  }

  def updateControl(permut: Vector[Int], control: Vector[Int], idx: Int) : Vector[Int] = {
    //TODO: implement
    control
  }




  /**
    *
    */

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
