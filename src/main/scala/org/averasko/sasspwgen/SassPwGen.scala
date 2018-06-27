package org.averasko.sasspwgen

import java.io.{File, PrintWriter}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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
      .map(s => pw.write(s))
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