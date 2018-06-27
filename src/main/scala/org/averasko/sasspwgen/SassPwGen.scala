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
    computeToOutput
  }

  def computeToOutput = {
    computeStream()
      .map(println)
      .map(s => 1)
      .foldLeft(0)((x1, x2) => x1 + x2)
  }

  def computeToFile(outFileName: String) = {
    val pw = new PrintWriter(new File(outFileName))
    computeStream()
      .map(s => pw.write(s))
      .map(s => 1)
      .foldLeft(0)((x1, x2) => x1 + x2)

    pw.close
  }

  def computeStream(): Seq[String] = {
    new org.averasko.sasspwgen.HairOfGloryStrategy().compute()
  }

  def timedFuture[T](future: Future[T]) = {
    val start = System.currentTimeMillis()
    future.onComplete({
      case _ => println(s"Done!\nFuture took ${System.currentTimeMillis() - start} ms")
    })
    future
  }



}