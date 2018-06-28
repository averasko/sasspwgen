package org.averasko.sasspwgen

import org.scalatest.FunSpec

class GeneratorsSpec extends FunSpec {
  describe("increasingNums") {
    it("increasingNums common case") {
      assert(Generators.increasingNums(2) ==
        Stream("", "0", "1", "01", "2", "12", "3", "23", "4", "34", "5", "45", "6", "56", "7", "67", "8", "78", "9", "89"),
        "bla")
    }

    it("") {
      assert(true)
    }

  }

}
