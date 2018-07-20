package org.averasko.sasspwgen.console

import java.io.{File, PrintWriter}

import org.averasko.sasspwgen.{HairOfGloryStrategy, Strategy}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object SassPwGen extends App {

  override def main(args: Array[String]): Unit = {

    timedFuture(computeFuture)

    println("computing...")
    readLine()
  }

  def computeFuture = Future{
    computeToOutput(new HairOfGloryStrategy())
  }

  def computeToOutput(strategy: Strategy) = {
    strategy.compute()
      .map(println)
      .map(s => 1)
      .foldLeft(0)((x1, x2) => x1 + x2)
  }

  def computeToFile(outFileName: String, strategy: Strategy) = {
    val pw = new PrintWriter(new File(outFileName))
    strategy.compute()
      .map(s => pw.println(s))
      .map(s => 1)
      .foldLeft(0)((x1, x2) => x1 + x2)

    pw.close
  }

  def timedFuture[T](future: Future[T]) = {
    val start = System.currentTimeMillis()
    future.onComplete({
      case _ => println(s"Done!\nFuture took ${System.currentTimeMillis() - start} ms")
    })
    future
  }



}