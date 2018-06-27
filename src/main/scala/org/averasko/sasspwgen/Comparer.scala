package org.averasko.sasspwgen

import java.io.File

import org.averasko.sasspwgen.SassPwGen.{computeFuture, timedFuture}

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

  def compare(srcName: String, dstName: String): Float = {

    val ENCODING = "ISO-8859-1"

    println(s"Loading data from ${srcName}...")
    val srcSet = Source.fromFile(new File(srcName), ENCODING).getLines().toSet

    println(s"Scanning data in ${dstName}...")
    val res = Source.fromFile(new File(dstName), ENCODING).getLines().map(dstLine => srcSet.contains(dstLine)).foldLeft((0,0))((ab, c) => if (c) (ab._1 + 1, ab._2) else (ab._1, ab._2 + 1))

    val ratio = (res._1 / (res._1 + res._2).toFloat) * 100
    println(s"Found ${res._1} matches and ${res._2} non-matches out of total ${res._1 + res._2} words which constitues ${ratio} percent.")
    ratio
  }
}
