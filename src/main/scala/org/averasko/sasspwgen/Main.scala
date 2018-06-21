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


  val len = 4;
  val a = Generators.permuts(len)
  println(s"permuts($len): $a")
  println(s"size = ${a.length}")

}