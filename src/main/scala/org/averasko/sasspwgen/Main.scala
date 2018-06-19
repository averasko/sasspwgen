package org.averasko.sasspwgen

object Main extends App {

  val list = "HairOfGlory"

  val list2 = Transforms.split(list)

  println("The list of possible passwords: " + list2.toString);

}