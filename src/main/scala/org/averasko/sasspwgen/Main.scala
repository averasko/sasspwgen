package org.averasko.sasspwgen

object Main extends App {

  val list = "HairOfGlory"
  println("Initial Data: " + list)

  val splittedList = Transforms.split(list)
  println("SplittedList: " + splittedList)

  val mergedList = Transforms.merge(splittedList)
  println("MergedList: " + mergedList)

  println("Numbers: " + Generators.numbers(3))

  println("Years: " + Generators.years)
  println("Months: " + Generators.months)
  println("Days: " + Generators.days)


  val len = 3;
  val a = Generators.permuts(len)
  println(s"permuts($len): $a")
  println(s"size = ${a.length}")


  val v = Vector("One", "Two", "Three")
  val s = Generators.permute(v).map(Transforms.merge)
  println(s"permuting: $s")



  val v2 = Combinators.insertRnd(List("One", "Two", "Three"), "Four")
  println(s"insertRnd(): $v2")
  println(s"capitalizeEasy(): ${Transforms.capitalizeEasy("DfiJeR")}")


  // ok, the real stuff
  val s1 = "Hair"
  val s2 = "Of"
  val s3 = "Glory"

  val w1 = Transforms.capitalizeEasy(s1).map(s => List(s))
  val w2 = Transforms.capitalizeEasy(s2)
  val w3 = Transforms.capitalizeEasy(s3)

  val w12 = w1.flatMap(l => Combinators.insertRnd(l, w2))

  val w123 = w12.flatMap(l => Combinators.insertRnd(l, w3))

  println(s"combinations: ${w123}")
  println(s"with length of ${w123.length}")

}