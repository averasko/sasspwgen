package org.averasko.sasspwgen

import org.scalatest.FunSpec

class GeneratorsSpec extends FunSpec {
  describe("lastIncValueIdx()") {
    it("should produce correct values on common inputs") {
      assert(Generators.lastIncValueIdx(List(1, 2, 3, 4)) == 2)
      assert(Generators.lastIncValueIdx(List(1, 4, 3, 2)) == 0)
      assert(Generators.lastIncValueIdx(List(4, 1, 2, 3)) == 2)
      assert(Generators.lastIncValueIdx(List(3, 2, 4, 1)) == 1)
    }

    it("should produce correct values on edge inputs") {
      assert(Generators.lastIncValueIdx(List()) == -1)
      assert(Generators.lastIncValueIdx(List(1)) == -1)
      assert(Generators.lastIncValueIdx(List(1, 2)) == 0)
      assert(Generators.lastIncValueIdx(List(2, 1)) == -1)
    }

  }

}
