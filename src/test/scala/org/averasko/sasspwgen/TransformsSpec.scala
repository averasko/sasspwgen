package org.averasko.sasspwgen

import org.scalatest.FunSpec


class TransformsSpec extends FunSpec {
    describe("concatenate") {
        it("common case") {
            assert (Transforms.concatenate(Stream("a", "b"), Stream("1", "2")) ==
                    Stream("a1", "a2", "b1", "b2"),
            "bla")
        }
    }

    describe("capitalizeEasy") {
        it("common case") {
            assert(Transforms.capitalizeEasy("AbraMat") == Stream("abramat", "Abramat", "ABRAMAT"), "bla")
        }
    }
}
