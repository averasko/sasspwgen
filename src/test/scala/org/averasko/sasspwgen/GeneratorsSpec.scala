package org.averasko.sasspwgen

import org.scalatest.FunSpec

class GeneratorsSpec extends FunSpec {
  describe("increasingNums") {
    it("common case") {
      assert(Generators.increasingNums(2) ==
        Stream("", "0", "1", "01", "2", "12", "3", "23", "4", "34", "5", "45", "6", "56", "7", "67", "8", "78", "9", "89"),
        "bla")
    }

//    it("large case") {
//      assert(Generators.increasingNums(10) ==
//        Stream("", "0", "1", "01", "2", "12", "3", "23", "4", "34", "5", "45", "6", "56", "7", "67", "8", "78", "9", "89"),
//        "bla")
//    }


  }


  describe("decreasingNums") {
    it("common case") {
      assert(Generators.decreasingNums(2) ==
        Stream("", "9", "8", "98", "7", "87", "6", "76", "5", "65", "4", "54", "3", "43", "2", "32", "1", "21", "0", "10"),
        "bla")
    }
  }

}
