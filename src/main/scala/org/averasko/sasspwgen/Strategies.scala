package org.averasko.sasspwgen

class Strategy {

  def compute() : Stream[String] = ???

}


object Strategies {



}


/**
  * We want this strategy to cover 80% of the most frequent passwords in the real lists.
  */
class EasyStrategy extends Strategy {
  override def compute() : Stream[String] = {

    Stream("password")


  }
}

class HairOfGloryStrategy extends Strategy {
  override def compute(): Stream[String] = {
    val s1 = "Hair"
    val s2 = "Of"
    val s3 = "Glory"

    val w1 = Transforms.capitalizeEasy (s1).map (s => List (s) )
    val w2 = Transforms.capitalizeEasy (s2)
    val w3 = Transforms.capitalizeEasy (s3)
    val w4 = Generators.numbers (1).map (String.valueOf)
    val w5 = Generators.symbols

    w1.flatMap (l => Combinators.insertRnd (l, w2) )
    .flatMap (l => Combinators.insertRnd (l, w3) )
    .flatMap (l => Combinators.insertRnd (l, w4) )
    .flatMap (l => Combinators.insertRnd (l, w5) )
    .map (Transforms.merge)
  }
}

