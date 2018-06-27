package org.averasko.sasspwgen

import org.averasko.sasspwgen.SassPwGen.{computeFuture, timedFuture}

object Comparer extends App {
  override def main(args: Array[String]): Unit = {

    if (args.length != 4) {
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
    println(s"Result: $res");
  }

  def printUsage(): Unit = {
    println("Usage: cmd --src <fileName> --dst <fileName>");
  }

  def compare(srcFile: String, dstFile: String): Float = {
    0.4f
  }
}
