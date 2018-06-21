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

  //println("AllPermuts: " + Generators.allPermuts(5))

  val l = Vector(1,2,3,4);
  println("findLastIncreaseIdx(" + l + ") = " + Generators.lastIncValueIdx(l))


  val len = 3;
  val a = Generators.permuts(len)
  println(s"permuts($len): $a")

}