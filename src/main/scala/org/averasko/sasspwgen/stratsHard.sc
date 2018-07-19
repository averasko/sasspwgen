import org.averasko.sasspwgen.{Comparer, EasyStrategy, HairOfGloryStrategy, SassPwGen}
// depends on ../SecLists/Passwords/...

val folder = "/Users/averasko/workspace/SecLists/Passwords/"

val fileNames = List("Leaked-Databases/000webhost.txt", "Leaked-Databases/Ashley-Madison.txt",
  "Leaked-Databases/alleged-gmail-passwords.txt", "Leaked-Databases/md5decryptor.uk.txt",
  "Leaked-Databases/rockyou.txt").map(s => folder + s)

val wordSet = ExhaustiveStrategy.compute().toSet

println(s" wordSet size = ${wordSet.size}")


fileNames.foreach(dstFileName => {
  val (wordUsage, wordCoverage) = Comparer.compare(wordSet, dstFileName)
  println(s"wordUsage = ${wordUsage}, wordCoverage = ${wordCoverage}.")
})


