package org.averasko.sasspwgen

object Combinators {

  //TODO: working with lists here is super inefficient
  def insertRnd(l: List[String], s: String): Stream[List[String]] = {
    def rec(h: List[String], t: List[String]) : Stream[List[String]] = {
      if (!t.isEmpty) {
        Stream((h :+ s) ++ t) ++ rec(h ++ List(t.head), t.tail)
      } else {
        Stream(h :+ s, h) //2 cases: inserting into the last pos and not inserting at all
      }
    }

    rec(List.empty, l)
  }

  def insertRnd(l: List[String], ss: Stream[String]): Stream[List[String]] = {
    ss.flatMap(s => insertRnd(l, s))
  }

}
