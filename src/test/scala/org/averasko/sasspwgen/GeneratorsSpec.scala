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
        Stream("", "0", "10", "1", "21", "2", "32", "3", "43", "4", "54", "5", "65", "6", "76", "7", "87", "8", "98", "9"),
        "bla")
    }
  }

  describe("repeatingNums") {
    it("common case") {
      assert(Generators.repeatingNums(2) ==
        Stream("", "0", "00", "1", "11", "2", "22", "3", "33", "4", "44", "5", "55", "6", "66", "7", "77", "8", "88", "9", "99"),
        "bla")
    }
  }

}
