package org.averasko.sasspwgen.console


import java.io.File

import org.averasko.sasspwgen.{EasyStrategy, Strategy}

import scala.io.Source
import scala.reflect.runtime.{universe => univ}

object Generator extends App {
  override def main(args: Array[String]): Unit = {

    if (!argsAreCorrect(args)) {
      println("Incorrect arguments")
      printUsage()
      return
    }

    var strategyName = "org.averasko.sasspwgen.EasyStrategy"
    var outFileName = ""
    args.sliding(2, 2).toList.collect {
      case Array("--strategy", newStrategyName: String) => strategyName = newStrategyName
      case Array("--output", newFileName: String) => outFileName = newFileName
      case any => { println("Incorrect arguments"); printUsage(); return; }
    }

    // load the strategy
    val m = univ.runtimeMirror(getClass.getClassLoader)
    println(s"m = $m")

    val strategyClass = m.staticClass(strategyName)
    println(s"strategyClass = $strategyClass")

    val classMirror = m.reflectClass(strategyClass)
    println(s"classMirror = $classMirror")

    val ctor = strategyClass.toType.decl(univ.termNames.CONSTRUCTOR).asMethod
    println(s"ctor = $ctor")

    val ctorm = classMirror.reflectConstructor(ctor)
    val zz = ctorm()
    println(s"zz = $zz")




    println("Done!")
    println(s"Result:")
  }

  def argsAreCorrect(args: Array[String]) = {
    args != null && (args.length == 0 || args.length == 2 || args.length == 4)
  }

  def printUsage(): Unit = {
    println("Runs a specified password generation strategy and spits the output into the console or a file.");
    println("Usage: --strategy <FullClassName> [ --output <fileName> ]");
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
