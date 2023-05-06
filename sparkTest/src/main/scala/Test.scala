import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import play.api.libs.json.Json

object Test {
  def main(args: Array[String]): Unit = {
    //    val files = FileSystem.get(sc)
/*
    val sparcConfig = new SparkConf();
    sparcConfig.setAppName("Word Count")
    val ctx = new SparkContext(sparcConfig)
    val tokenized = ctx.textFile("hdfs://localhost:9000/movies/tmp")*/
/*
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Spark Structured Streaming with Kafka Demo")
      .config("spark.cassandra.connection.host", "localhost")
      .getOrCreate()



    val data = spark.read.text("hdfs://localhost:9000/movies/tmp")

    data.coalesce(1).write.mode(SaveMode.Append).text("hdfs://localhost:9000/movies/master/")*/

    val testJson = "{\"movieId\":46,\"movieRating\":8,\"userId\":17,\"timestamp\":1612726181630}"

    val js = Json.parse(testJson)

    println(js.toString())
    println(js("timestamp"))

  }
}
