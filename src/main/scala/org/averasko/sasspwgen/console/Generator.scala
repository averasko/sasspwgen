package org.averasko.sasspwgen.console

import java.io.{File, PrintWriter}

import org.averasko.sasspwgen.Strategy

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
    var silentFlag = false
    args.sliding(2, 2).toList.collect {
      case Array("--strategy", newStrategyName: String) => strategyName = newStrategyName
      case Array("--output", newFileName: String) => outFileName = newFileName
      case Array("--silent", newSilentFlag: String) => silentFlag = newSilentFlag.toLowerCase().equals("yes")
      case any => { println("Incorrect arguments"); printUsage(); return; }
    }

    userPrintln(s"Instantiating strategy $strategyName...", silentFlag)
    val strategy = instantiatedStrategy(strategyName)

    if (outFileName.isEmpty) {
      strategy.compute().map(s => println(s)).foldLeft(0)((a,b) => a + 1)
    } else {
      computeToFile(strategy, outFileName)
    }

    userPrintln("Done!", silentFlag)
  }

  def userPrintln(line: String, silent: Boolean) = {
    if (!silent) {
      println(line)
    }
  }

  def instantiatedStrategy(strategyName: String): Strategy = {
    val m = univ.runtimeMirror(getClass.getClassLoader)
    val strategyClass = m.staticClass(strategyName)
    val classMirror = m.reflectClass(strategyClass)
    val ctor = strategyClass.toType.decl(univ.termNames.CONSTRUCTOR).asMethod
    val ctorm = classMirror.reflectConstructor(ctor)
    val obj = ctorm()
    obj.asInstanceOf[Strategy]
  }

  def argsAreCorrect(args: Array[String]) = {
    args != null && (args.length == 0 || args.length == 2 || args.length == 4 || args.length == 6)
  }

  def printUsage(): Unit = {
    println("Runs a specified password generation strategy and spits the output into the console or a file.");
    println("Usage: [ --strategy <FullClassName> ] [ --output <fileName> ] [ --silent <yes/no>] ");
  }

  def computeToFile(strategy: Strategy, outFileName: String) = {
    val pw = new PrintWriter(new File(outFileName))
    strategy.compute()
      .map(s => pw.println(s))
      .map(s => 1)
      .foldLeft(0)((x1, x2) => x1 + x2)

    pw.close
  }

}
