import org.averasko.sasspwgen.console.Comparer
import org.averasko.sasspwgen.{EasyStrategy, HairOfGloryStrategy}
// depends on ../SecLists/Passwords/...

// 3 lists of ~100 passwords

val folder = "/Users/averasko/workspace/SecLists/Passwords/"
val fileNames = List("darkweb2017-top100.txt", "probable-v2-top207.txt",
  "twitter-banned.txt", "darkweb2017-top10.txt", "darkweb2017-top1000.txt",
  "darkweb2017-top10000.txt").map(s => folder + s)

val wordSet = new EasyStrategy().compute().toSet

println(s" wordSet size = ${wordSet.size}")

fileNames.foreach(dstFileName => {
  val ratio = Comparer.compare(wordSet, dstFileName)
  println(s"Matching '$dstFileName' \n=> $ratio %")
})


