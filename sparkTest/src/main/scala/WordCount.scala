import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val sparcConfig = new SparkConf();
    sparcConfig.setAppName("Word Count")
    val ctx = new SparkContext(sparcConfig)


//    val tokenized = ctx.textFile("hdfs://" + args[0] + "/" + args[1])
//    val tokenized = ctx.textFile("hdfs://hadoop-master:9000/test/b.txt").flatMap(_.split(" "))
    val tokenized = ctx.textFile("hdfs://localhost:9000/test/book.txt").flatMap(_.split(" "))
    val wordCounts = tokenized.map((_, 1)).reduceByKey(_ + _)

//    System.out.println(wordCounts.collect().mkString(", "))


    wordCounts.saveAsTextFile("hdfs://localhost:9000/res")
    ctx.stop()
  }
}
