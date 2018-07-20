package org.averasko.sasspwgen.console

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Runs Generator.main() but count the time it takes as well.
  */
object SassPwGen extends App {

  override def main(args: Array[String]): Unit = {

    timedFuture(computeFuture(args))

    readLine()
  }

  def computeFuture(args: Array[String]) = Future{
    Generator.main(args)
  }


  def timedFuture[T](future: Future[T]) = {
    val start = System.currentTimeMillis()
    future.onComplete({
      case _ => println(s"Done!\nFuture took ${System.currentTimeMillis() - start} ms")
    })
    future
  }

}