package org.averasko.sasspwgen

import java.io.File

import scala.io.Source

object Comparer extends App {
  override def main(args: Array[String]): Unit = {

    if (args.length != 4 && args.length != 0) {
      println("Incorrect arguments"); printUsage(); return;
    }

    var srcName = "src.txt"
    var dstName = "dst.txt"
    args.sliding(2, 2).toList.collect {
      case Array("--src", src: String) => srcName = src
      case Array("--dst", dst: String) => dstName = dst
      case any => { println("Incorrect arguments"); printUsage(); return; }
    }

    println(s"Comparing file '${srcName}' to the target pwd file '${dstName}' ...")

    val res = compare(srcName, dstName)

    println("Done!")
    println(s"Result: $res")
  }

  def printUsage(): Unit = {
    println("Usage: --src <fileName> --dst <fileName>");
  }

  def compare(srcName: String, dstName: String): (Float, Float) = {
    println(s"Loading data from ${srcName}...")
    compare(cachePasswords(srcName), dstName)
  }

  def encoding() = "ISO-8859-1"

  def cachePasswords(fileName : String) : Set[String] = Source.fromFile(new File(fileName), encoding()).getLines().toSet

  def compare(wordSet: Set[String], dstName: String): (Float, Float) = {
    println(s"Scanning data in ${dstName}...")
    val res = Source.fromFile(new File(dstName), encoding()).getLines().map(dstLine => wordSet.contains(dstLine)).foldLeft((0,0))((ab, c) => if (c) (ab._1 + 1, ab._2) else (ab._1, ab._2 + 1))

    val wordUsage = res._1 / wordSet.size.toFloat * 100
    val wordCoverage = (res._1 / (res._1 + res._2).toFloat) * 100
    println(s"Found ${res._1} matches and ${res._2} non-matches out of total ${res._1 + res._2} words.")
    (wordUsage, wordCoverage)
  }
}
