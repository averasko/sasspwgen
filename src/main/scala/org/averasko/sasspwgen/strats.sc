import org.averasko.sasspwgen.{Comparer, EasyStrategy, HairOfGloryStrategy, SassPwGen}
// depends on ../SecLists/Passwords/...

// 3 lists of ~100 passwords

val folder = "/Users/averasko/workspace/SecLists/Passwords/"
val fileNameEasy = List("darkweb2017-top100.txt", "probable-v2-top207.txt",
  "twitter-banned.txt", "darkweb2017-top10.txt", "darkweb2017-top1000.txt",
  "darkweb2017-top10000.txt").map(s => folder + s)

val fileNameHard = List("Leaked-Databases/000webhost.txt", "Leaked-Databases/Ashley-Madison.txt",
  "Leaked-Databases/alleged-gmail-passwords.txt", "Leaked-Databases/md5decryptor.uk.txt",
  "Leaked-Databases/rockyou.txt").map(s => folder + s)


val fileNames = fileNameHard

val wordSet = EasyStrategy.compute().toSet

println(s" wordSet size = ${wordSet.size}")

fileNames.foreach(dstFileName => {
  val ratio = Comparer.compare(wordSet, dstFileName)
  println(s"Matching '$dstFileName' \n=> $ratio %")
})


