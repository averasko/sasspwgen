package org.averasko.sasspwgen

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val s1 = "Hair"
    val s2 = "Of"
    val s3 = "Glory"

    val w1 = Transforms.capitalizeEasy(s1).map(s => List(s))
    val w2 = Transforms.capitalizeEasy(s2)
    val w3 = Transforms.capitalizeEasy(s3)
    val w4 = Generators.numbers(1).map(String.valueOf)
    val w5 = Generators.symbols

    val w12 = w1.flatMap(l => Combinators.insertRnd(l, w2))

    val w123 = w12.flatMap(l => Combinators.insertRnd(l, w3))

    val w1234 = w123.flatMap(l => Combinators.insertRnd(l, w4))

    val w12345 = w1234.flatMap(l => Combinators.insertRnd(l, w5)).map(Transforms.merge)

    w12345.map(println)
    //println(s"with length of ${w1234.length}")

  }

}