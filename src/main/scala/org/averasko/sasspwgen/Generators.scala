package org.averasko.sasspwgen

import scala.collection.mutable.ArrayBuffer

object Generators {
  /**
    * Steinhaus–Johnson–Trotter algorithm
    * @see https://en.wikipedia.org/wiki/Steinhaus%E2%80%93Johnson%E2%80%93Trotter_algorithm
    */
  def permuts(n : Int): Stream[Vector[Int]] = {
    //TODO: can we make control vector to be mutable? or not?
    def rec(permut: Vector[Int], control: Vector[Int]) : Stream[Vector[Int]] = {
      val (newPermut, newControl) = nextStep(permut, control)
      if (newPermut.length > 0) {
        Stream(permut) ++ rec(newPermut, newControl)
      } else {
        Stream(permut)
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
    var idxOfLargest = -1;
    for (i <- 0 until permut.length) {
      if (control(i) != 0 && (idxOfLargest < 0 || permut(i) > permut(idxOfLargest))) {
        idxOfLargest = i
      }
    }
    idxOfLargest
  }

  def swap(v: Vector[Int], i1: Int, i2: Int): Vector[Int] = {
    var tmp = v(i1)
    var res = v.updated(i1, v(i2))
    res.updated(i2, tmp)
  }

  def updateControl(permut: Vector[Int], control: Vector[Int], idx: Int) : Vector[Int] = {
    var newControl = control
    val chosenValue = permut(idx)
    for (i <- 0 to control.length - 1) {
      if (permut(i) > chosenValue) {
        if (i < idx) {
          newControl = newControl.updated(i, +1)
        } else if (i > idx) {
          newControl = newControl.updated(i, -1)
        }
      }
    }
    newControl
  }


  def numbers(maxLength : Int) : Stream[Int] = (0 to Math.round(Math.pow(10.0f, maxLength).toFloat) - 1).toStream


  def years : Stream[String] = (1950 to 2030).map(Integer.toString).toStream
  def months : Stream[String] = (1 to 12).map(Integer.toString).toStream
  //TODO: add leading zeroes

  def days : Stream[String] = (1 to 31).map(Integer.toString).toStream
  //TODO: add leading zeroes

  def symbols : Stream[String] = Stream("~","!","?","@","#","$","%","^","&","*","(",")","_","+")



  def combine(ss : Vector[String]) : Stream[Vector[String]] = {
    permuts(ss.length).map(vi => vi.map(ss(_)))
  }


}
