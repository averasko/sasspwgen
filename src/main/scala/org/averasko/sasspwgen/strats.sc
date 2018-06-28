import org.averasko.sasspwgen.{Comparer, EasyStrategy, HairOfGloryStrategy, SassPwGen}
// depends on ../SecLists/Passwords/...

// 3 lists of ~100 passwords

val folder = "/Users/averasko/workspace/SecLists/Passwords/"
val fileNameEasy = List("darkweb2017-top100.txt", "probable-v2-top207.txt", "twitter-banned.txt").map(s => folder + s)


val wordSet = new EasyStrategy().compute().toSet
println(s" wordSet size = ${wordSet.size}")

fileNameEasy.foreach(dstFileName => {
  val ratio = Comparer.compare(wordSet, dstFileName)
  println(s"Matching '$dstFileName' \n=> $ratio %")
})


