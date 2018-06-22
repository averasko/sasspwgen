package org.averasko.sasspwgen

object Combinators {

  //TODO: working with lists here is super inefficient
  def insertRnd(seq: Seq[List[String]], s: String): Seq[List[String]] = {
    def rec(h: List[String], t: List[String]) : Seq[List[String]] = {
      if (!t.isEmpty) {
        Seq((h :+ s) ++ t) ++ rec(h ++ List(t.head), t.tail)
      } else {
        Seq(h :+ s)
      }
    }

    seq.flatMap(l => rec(List.empty, l))
  }

}
