package org.averasko.sasspwgen

class Strategy {

  def compute() : Stream[String] = ???

}


object Strategies {



}


/**
  * We want this strategy to cover 80% of the most frequent passwords in the real lists.
  */
object EasyStrategy extends Strategy {
  override def compute() : Stream[String] = {

      Generators.allWordsHard ++
      Generators.allTrailingNums() ++
      Transforms.concatenate(Generators.allWordsEasy, Generators.allTrailingNums(3)) ++
      Generators.keyboardCombinationsHard

  }
}

/**
  * This strategy should cover some significant percentage of the passwords of the real-word leaked databases.
  * The aim is 50% coverage.
  * Computation-wise, it should fit the largest instances on DigitalOcean: ~100 GB with ~30 CPUs
  */
object ExhaustiveStrategy extends Strategy {
  override def compute() : Stream[String] = {
    //Generators.allNames
    //Generators.allTrailingNums(5)
    Transforms.capitalizeEasy(Generators.words3)

  }
}

object HairOfGloryStrategy extends Strategy {
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

