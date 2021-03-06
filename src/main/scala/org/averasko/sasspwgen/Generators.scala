package org.averasko.sasspwgen

import java.io.File

import scala.io.Source

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


  def maxValue(len : Int): Int = Math.round(Math.pow(10.0f, len).toFloat) - 1

  def numbers(maxLength : Int) : Stream[Int] = (0 to maxValue(maxLength)).toStream


  def years : Stream[String] = (1950 to 2030).map(Integer.toString).toStream
  def months : Stream[String] = (1 to 12).map(Integer.toString).toStream
  //TODO: add leading zeroes

  def days : Stream[String] = (1 to 31).map(Integer.toString).toStream
  //TODO: add leading zeroes

  def sym1 : Stream[String] = Stream("~","`","!","@","#","$","%","^","&","*","(",")","_","+","-","=")
  def sym2 : Stream[String] = Stream("[","]","{","}","\\","|",";",":","'","\"",",",".","<",">","/","?")
  def sym3 = sym1 ++ sym2


  def combine(ss : Vector[String]) : Stream[Vector[String]] = {
    permuts(ss.length).map(vi => vi.map(ss(_)))
  }

  def usernames = wordsFromFile("../SecLists/Usernames/top-usernames-shortlist.txt").toStream


  def maleNames1000 = wordsFromFile("../SecLists/Usernames/Names/malenames-usa-top1000.txt").toStream.map(s => s.toLowerCase)
  def femaleNames1000 = wordsFromFile("../SecLists/Usernames/Names/femalenames-usa-top1000.txt").toStream.map(s => s.toLowerCase)
  def familyNames1000 = wordsFromFile("../SecLists/Usernames/Names/familynames-usa-top1000.txt").toStream.map(s => s.toLowerCase)
  def names = wordsFromFile("../SecLists/Usernames/Names/names.txt").toStream

  def firstNames2000 = maleNames1000 ++ femaleNames1000
  def familyNames = familyNames1000 ++ names

  def allNames = firstNames2000 ++ familyNames



  def words1 = wordsFromFile("../dictionary/popular.txt").toStream
  def words2 = wordsFromFile("../dictionary/ospd.txt").toStream
  def words3 = wordsFromFile("../dictionary/enable1.txt").toStream

  def keyboardCombinationsHard = wordsFromFile("../SecLists/Passwords/Keyboard-Combinations.txt").toStream

  def wordsFromFile(fileName : String) = Source.fromFile(new File(fileName), "ISO-8859-1").getLines()




  def keyboardCombinationsEasy = Stream("querty", "zxcvbn")

  def allWordsEasy = usernames ++ keyboardCombinationsEasy ++ names ++ words1
  def allWordsHard = usernames ++ keyboardCombinationsEasy ++ names ++ words3


  def allTrailingNums(max: Int = 10) =
    Generators.increasingNums(max) ++
      Generators.decreasingNums(max) ++
      Generators.repeatingNums(max)

  def trailingNums(maxLength: Int = 10, nextValue: (Int) => Int) : Stream[String] = {
    def endsWith(len: Int, value : Int, tail: List[Int]) : Stream[List[Int]] = {
      if (len < maxLength && (value >= 0 && value <= 9)) {
        Stream(tail) ++ endsWith(len + 1, nextValue(value), List(value) ++ tail)
      } else {
        Stream(tail)
      }
    }

    (Stream(List()) ++ Stream.from(0, 1).take(10).flatMap(n => endsWith(1, nextValue(n), List(n))))
      .map(lOfI => lOfI.map(i => i.toString)).map(Transforms.merge)
  }

  def increasingNums(maxLength: Int = 10) : Stream[String] = trailingNums(maxLength, n => n - 1)

  def decreasingNums(maxLength: Int = 10) : Stream[String] = trailingNums(maxLength, n => n + 1)

  def repeatingNums(maxLength: Int = 10) : Stream[String] = trailingNums(maxLength, identity)


  //TODO: optimize to make them real and covering the ones starting from 0...
  def zipcodes() : Stream[String] = (100000 to 999999).map(Integer.toString).toStream

  def DOBs() : Stream[String] = (1920 to 2020).toStream.flatMap(y => (1 to 12).toStream.map(m => (y, m))).
    flatMap(ym => (1 to 31).toStream.map(d => (ym._1, ym._2, d))).map(ymd => (ymd._1.toString, ymd._2.toString, ymd._3.toString)).
    flatMap(ymd => Stream(
      ymd._1 + ymd._2 + ymd._3,
      ymd._3 + ymd._2 + ymd._1,
      ymd._2 + ymd._3 + ymd._1,
      ymd._2 + ymd._3,
      ymd._3 + ymd._2))


  // TODO: omg omg !!!
  //def phones() : Stream[String] = (1 to 10).map(Integer.toString).toStream.flatMap(h => (0 to 999999999).toStream.map(Integer.toString).map(t => h + t))
  // making it a smaller set (1M only)
  def phones() : Stream[String] = (1000000000 to 1000999999).toStream.map(Integer.toString)


}
