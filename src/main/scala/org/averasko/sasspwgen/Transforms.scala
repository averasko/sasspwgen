package org.averasko.sasspwgen

object Transforms {

  def nextIndex(s : String) : Int = s match {
    case "" => 0
    case s => {
      var i = 1;
      while(i < s.length && !Character.isUpperCase(s.charAt(i))) {
        i = i + 1
      }
      i
    }
  }

  def split(s : String) : List[String] = splitBy(s, (s : String) => nextIndex(s))

  def splitBy(s : String, f: (String) => Int) : List[String] = {
    def rec(res : List[String], rest : String) : List[String] = {
      rest match {
        case "" => res
        case s2 => {
          val i = f(s2)
          rec(s2.substring(0, i) :: res, s2.substring(i))
        }
      }
    }

    rec(List.empty, s)
  }

  def merge(l : List[String]) : String = l.fold("")((s1, s2) => s1 + s2)



}