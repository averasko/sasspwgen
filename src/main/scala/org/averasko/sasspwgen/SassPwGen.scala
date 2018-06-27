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
    val s1 = "Hair"
    val s2 = "Of"
    val s3 = "Glory"

    val w1 = Transforms.capitalizeEasy(s1).map(s => List(s))
    val w2 = Transforms.capitalizeEasy(s2)
    val w3 = Transforms.capitalizeEasy(s3)
    val w4 = Generators.numbers(1).map(String.valueOf)
    val w5 = Generators.symbols

    w1.flatMap(l => Combinators.insertRnd(l, w2))
      .flatMap(l => Combinators.insertRnd(l, w3))
      .flatMap(l => Combinators.insertRnd(l, w4))
      .flatMap(l => Combinators.insertRnd(l, w5))
      .map(Transforms.merge)
  }




  def timedFuture[T](future: Future[T]) = {
    val start = System.currentTimeMillis()
    future.onComplete({
      case _ => println(s"Done!\nFuture took ${System.currentTimeMillis() - start} ms")
    })
    future
  }



}